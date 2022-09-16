package nl.sagemc.creativeworlds.api.commandhandler

abstract class ArgumentParser<E> {
    abstract fun parse(string: String): E?
    abstract fun completions(): Array<String>

    infix fun id(identifier: String): IdentifiedParser<E> {
        return IdentifiedParser(this, identifier)
    }
}

class IdentifiedParser <E> (val parser: ArgumentParser<E>, val identifier: String)