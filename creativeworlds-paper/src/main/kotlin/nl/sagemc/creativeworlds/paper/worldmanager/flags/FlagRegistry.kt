package nl.sagemc.creativeworlds.paper.worldmanager.flags

object FlagRegistry {
    val flags: HashMap<String, Flag<*>> = HashMap()

    fun registerFlags(flag: Flag<*>) {
        if (flags.containsKey(flag.name)) {
            //TODO: Gooi error
        }

        flags[flag.name] = flag
    }
}