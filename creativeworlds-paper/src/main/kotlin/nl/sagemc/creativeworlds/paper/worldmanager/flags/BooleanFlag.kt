package nl.sagemc.creativeworlds.paper.worldmanager.flags

open class BooleanFlag(name: String, value: Boolean) : Flag<Boolean>(name, value) {
    override fun serialize(obj: Boolean): String {
        return obj.toString()
    }

    override fun deserialize(obj: String): Boolean {
        return obj.toBooleanStrict()
    }
}