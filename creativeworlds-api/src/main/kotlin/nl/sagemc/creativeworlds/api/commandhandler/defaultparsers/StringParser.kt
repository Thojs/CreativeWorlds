package nl.sagemc.creativeworlds.api.commandhandler.defaultparsers

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser

object StringParser : ArgumentParser<String>() {
    override fun parse(string: String) = string

    override fun completions() = emptyArray<String>()
}