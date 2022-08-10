package nl.sagemc.creativeworlds.api.commandhandler

interface ArgumentParser<T> {
    fun parse(string: String): T?
    fun completions(): Array<String>
}