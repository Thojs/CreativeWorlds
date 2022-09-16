package nl.sagemc.creativeworlds.api.commandhandler.defaultparsers

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser

object IntegerParser : ArgumentParser<Int>() {
    override fun parse(string: String) = string.toIntOrNull()

    override fun completions(): Array<String> = emptyArray()
}