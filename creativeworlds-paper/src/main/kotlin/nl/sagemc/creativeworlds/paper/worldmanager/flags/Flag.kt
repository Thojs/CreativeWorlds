package nl.sagemc.creativeworlds.paper.worldmanager.flags

abstract class Flag<E>(val name: String, var value: E) {
    abstract fun serialize(obj: E): String
    abstract fun deserialize(obj: String): E
}