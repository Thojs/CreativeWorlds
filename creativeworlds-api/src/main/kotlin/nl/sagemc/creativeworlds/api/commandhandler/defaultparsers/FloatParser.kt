package nl.sagemc.creativeworlds.api.commandhandler.defaultparsers

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser

object FloatParser : ArgumentParser<Float>() {
    override fun parse(string: String) = string.toFloatOrNull()

    override fun completions(): Array<String> = emptyArray()
}