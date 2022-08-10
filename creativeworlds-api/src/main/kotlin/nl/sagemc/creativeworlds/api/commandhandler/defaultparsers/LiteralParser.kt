package nl.sagemc.creativeworlds.api.commandhandler.defaultparsers

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser

class LiteralParser(private vararg val literals: String) : ArgumentParser<String> {
    override fun parse(string: String): String? {
        if (string.isEmpty()) return null
        if (!literals.contains(string)) return null

        return string
    }

    override fun completions() = literals.toList().toTypedArray()
}