package nl.sagemc.creativeworlds.paper.worldmanager.flags

import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld

abstract class Flag<E>(val name: String, val defaultValue: E) {
    abstract fun serialize(obj: E): String
    abstract fun deserialize(obj: String): E?
    abstract fun onChange(world: CreativeWorld, newValue: E)
}