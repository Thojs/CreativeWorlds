package nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags

import nl.sagemc.creativeworlds.paper.worldmanager.flags.Flag

class GreetingFlag : Flag<String>("greeting", "") {
    override fun serialize(obj: String) = obj

    override fun deserialize(obj: String) = obj
}