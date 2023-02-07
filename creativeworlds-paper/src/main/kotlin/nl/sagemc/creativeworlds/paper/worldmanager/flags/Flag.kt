package nl.sagemc.creativeworlds.paper.worldmanager.flags

import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import org.bukkit.configuration.ConfigurationSection

abstract class Flag<E>(val name: String, val defaultValue: E) {
    abstract fun serialize(obj: E, section: ConfigurationSection)
    abstract fun deserialize(obj: ConfigurationSection): E?
    abstract fun onChange(world: CreativeWorld, newValue: E)
}