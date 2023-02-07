package nl.sagemc.creativeworlds.paper.worldmanager.flags

import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import org.bukkit.configuration.ConfigurationSection

class FlagContainer(private val world: CreativeWorld, private val section: ConfigurationSection, val update: () -> Unit) {
    private val flags = hashMapOf<Flag<*>, Any>()

    init {
        globalFlags.forEach {
            it.defaultValue?.let { it1 -> flags[it] = it1 }
        }
    }
    
    operator fun <E : Any> set(flag: Flag<E>, value: E) {
        flag.serialize(value, section.getConfigurationSection(flag.name) ?: section.createSection(flag.name))
        flags[flag] = value
        flag.onChange(world, value)
        update()
    }

    fun <E : Any> reset(flag: Flag<E>) {
        set(flag, flag.defaultValue)
    }
    
    operator fun <E> get(flag: Flag<E>): E {
        if (!flags.containsKey(flag) && section.contains(flag.name)) {
            flags[flag] = section.getConfigurationSection(flag.name)?.let { flag.deserialize(it) } ?: flag.defaultValue as Any
        }
        return flags[flag] as? E ?: flag.defaultValue
    }

    fun getCommandFlags(): List<CommandFlag<*>> {
        val list = mutableListOf<CommandFlag<*>>()

        flags.keys.forEach {
            if (it is CommandFlag) list += it
        }

        return list
    }

    companion object {
        val globalFlags = mutableListOf<Flag<*>>()
    }
}