package nl.sagemc.creativeworlds.paper.worldmanager.flags

interface Flag<E> {
    val name: String

    fun serialize(obj: E): String
    fun deserialize(): E
}