package nl.sagemc.creativeworlds.paper.worldmanager.flags

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser
import org.bukkit.configuration.ConfigurationSection

class FlagContainer(section: ConfigurationSection) {
    private val flags: HashMap<Flag<*>, Any> = HashMap()

    fun registerFlag(flag: Flag<*>) {
        flags[flag] = flag.defaultValue as Any
    }
    
    operator fun <E : Any> set(flag: Flag<E>, value: E) {
        if (flags.containsKey(flag)) throw IllegalStateException("Flag is not registered to FlagContainer.")
        flags[flag] = value
    }
    
    operator fun <E> get(flag: Flag<E>): E {
        return flags[flag] as E
    }

    val flagParser = object : ArgumentParser<Flag<*>> {
        override fun parse(string: String): Flag<*>? {
            return flags.keys.find { it.name == string }
        }

        override fun completions() = flags.keys.map { it.name }.toTypedArray()
    }
}