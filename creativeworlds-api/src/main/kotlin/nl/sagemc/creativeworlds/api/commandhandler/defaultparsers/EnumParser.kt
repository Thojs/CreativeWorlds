package nl.sagemc.creativeworlds.api.commandhandler.defaultparsers

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser

class EnumParser<T : Enum<T>>(private val enumClass: Class<T>, private val lowercase: Boolean = false) : ArgumentParser<T>() {
    override fun parse(string: String) = enumClass.enumConstants.find { it.name.equals(string, true) }

    override fun completions() = enumClass.enumConstants.map { if (lowercase) it.name.lowercase() else it.name }.toTypedArray()
}