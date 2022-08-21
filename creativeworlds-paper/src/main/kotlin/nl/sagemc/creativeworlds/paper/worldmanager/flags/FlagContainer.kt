package nl.sagemc.creativeworlds.paper.worldmanager.flags

import org.bukkit.configuration.ConfigurationSection

class FlagContainer(section: ConfigurationSection) {
    private val flags: HashMap<Flag<*>, Any> = HashMap()
    
    operator fun <E : Any> set(flag: Flag<E>, value: E) {
        flags[flag] = value
    }
    
    operator fun <T> get(flag: Flag<T>): T {
        return flags[flag] as T
    }
}