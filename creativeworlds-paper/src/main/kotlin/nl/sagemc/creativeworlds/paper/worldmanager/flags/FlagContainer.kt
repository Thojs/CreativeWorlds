package nl.sagemc.creativeworlds.paper.worldmanager.flags

import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import org.bukkit.configuration.ConfigurationSection

class FlagContainer(private val world: CreativeWorld, private val section: ConfigurationSection, val update: () -> Unit) {
    private val flags = hashMapOf<Flag<*>, Any>()
    
    operator fun <E : Any> set(flag: Flag<E>, value: E) {
        section[flag.name] = flag.serialize(value)
        flags[flag] = value
        flag.onChange(world, value)
        update()
    }
    
    operator fun <E> get(flag: Flag<E>): E {
        if (!flags.containsKey(flag) && section.contains(flag.name)) {
            flags[flag] = section.getString(flag.name)?.let { flag.deserialize(it) } ?: flag.defaultValue as Any
        }
        return flags[flag] as? E ?: flag.defaultValue
    }
}