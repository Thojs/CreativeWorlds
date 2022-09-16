package nl.sagemc.creativeworlds.paper.worldmanager.flags

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser
import org.bukkit.configuration.ConfigurationSection

class FlagContainer(private val section: ConfigurationSection) {
    private val flags = mutableListOf<Flag<*>>()

    fun <E : Any> registerFlag(flag: Flag<E>) {
        flag.value = section.getString(flag.name)?.let { flag.deserialize(it) } ?: flag.value
        flags.add(flag)
    }
    
    operator fun <E : Any> set(flag: Flag<E>, value: E) {
        if (flags.contains(flag)) throw IllegalStateException("Flag is not registered to FlagContainer.")
        flag.value = value
        section[flag.name] = flag.serialize(value)
    }
    
    operator fun <E> get(flag: Flag<E>): E {
        if (!flags.contains(flag)) throw IllegalArgumentException("Flag is not registered to FlagContainer.")
        return flag.value
    }

    val flagParser = object : ArgumentParser<Flag<*>>() {
        override fun parse(string: String): Flag<*>? {
            return flags.find { it.name == string }
        }

        override fun completions() = flags.map { it.name }.toTypedArray()
    }
}