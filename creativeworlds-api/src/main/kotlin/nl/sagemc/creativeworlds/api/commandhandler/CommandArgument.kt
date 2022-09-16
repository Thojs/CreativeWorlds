package nl.sagemc.creativeworlds.api.commandhandler

open class CommandArgument<S, E>(val source: S, val identifier: String, val parser: ArgumentParser<E>) {
    val arguments: MutableList<CommandArgument<S, *>> = ArrayList()

    var executor: ((arguments: Arguments) -> Unit)? = null
        private set

    /**
     * Optional description about this argument. By default, it's an empty string.
     */
    var description: String = ""

    /**
     * Sets the executor of this argument. This function will be called when a source executes the given command.
     * @param executor The new executor
     */
    fun executor(executor: (arguments: Arguments) -> Unit) {
        this.executor = executor
    }

    /**
     * Adds a child argument to this argument node.
     * @param parser The parser for the new argument
     * @param argument The new argument.
     */
    fun <E> argument(parser: IdentifiedParser<E>, argument: CommandArgument<S, E>.() -> Unit) {
        val arg = CommandArgument(source, parser.identifier, parser.parser).apply(argument)
        appendArgument(arg)
    }

    /**
     * Appends a child argument to the current argument.
     * @param argument The child argument to append.
     */
    fun appendArgument(argument: CommandArgument<S, *>) {
        arguments.add(argument)
    }
}