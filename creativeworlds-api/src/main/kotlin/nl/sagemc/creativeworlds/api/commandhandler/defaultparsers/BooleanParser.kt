package nl.sagemc.creativeworlds.api.commandhandler.defaultparsers

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser

object BooleanParser : ArgumentParser<Boolean>() {
    override fun parse(string: String) = string.toBooleanStrictOrNull()

    override fun completions() = arrayOf("true", "false")
}