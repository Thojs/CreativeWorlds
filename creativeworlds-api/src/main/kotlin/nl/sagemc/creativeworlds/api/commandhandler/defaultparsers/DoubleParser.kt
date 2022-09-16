package nl.sagemc.creativeworlds.api.commandhandler.defaultparsers

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser

object DoubleParser : ArgumentParser<Double>() {
    override fun parse(string: String) = string.toDoubleOrNull()

    override fun completions(): Array<String> = emptyArray()
}