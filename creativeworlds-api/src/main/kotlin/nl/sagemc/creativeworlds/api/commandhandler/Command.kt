package nl.sagemc.creativeworlds.api.commandhandler

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser

open class Command<T: Any>(val name: String, vararg val aliases: String) {
    private var command: RootCommand<T>? = null

    fun command(command: RootCommand<T>.() -> Unit) {
        this.command = RootCommand<T>().apply(command)
    }

    class RootCommand<T: Any> internal constructor() : CommandArgument<T>(LiteralParser())

    open class CommandArgument<T: Any>(val parser: ArgumentParser<*>) {
        private var requires: MutableList<(source: T) -> Boolean> = ArrayList()

        /**
         * Adds a requirement to this argument, sources that do not have this requirement will not see the suggestions.
         * @param requirement The requirement function that returns if the source has access or not.
         */
        fun require(requirement: (source: T) -> Boolean) {
            requires.add(requirement)
        }

        internal fun requires(source: T): Boolean {
            requires.forEach {
                if (!it(source)) return false
            }
            return true
        }

        internal val arguments: MutableList<CommandArgument<T>> = ArrayList()

        /**
         * Adds a child argument.
         * @param parser The argument parser.
         * @param argument The argument.
         */
        fun argument(parser: ArgumentParser<*>, argument: CommandArgument<T>.() -> Unit) : CommandArgument<T> {
            val appliedArgument = CommandArgument<T>(parser).apply(argument)
            appendArguments(appliedArgument)
            return appliedArgument
        }

        fun arguments(vararg parser: ArgumentParser<*>, argument: CommandArgument<T>.() -> Unit) {
            if (parser.isEmpty()) return

            val firstArgument = CommandArgument<T>(parser[0]).apply(argument)
            firstArgument.executor = null

            var newArgument: CommandArgument<T>? = null

            parser.drop(1).forEachIndexed { index, argumentParser ->
                val arg = CommandArgument<T>(argumentParser).apply(argument)
                arg.requires.clear()
                if (index != parser.lastIndex) arg.executor = null

                if (newArgument == null) {
                    firstArgument.appendArguments(arg)
                    newArgument = arg
                    return@forEachIndexed
                }

                newArgument?.appendArguments(arg)
                newArgument = arg
            }
        }

        private var executor: ((source: T, arguments: Array<Any>) -> Unit)? = null

        internal fun executor(source: Any, arguments: Array<Any>) {
            executor?.invoke(source as T, arguments)
        }

        fun execute(executor: (source: T, arguments: Array<Any>) -> Unit) {
            this.executor = executor
        }

        fun appendArguments(vararg arguments: CommandArgument<T>) {
            this.arguments.addAll(arguments)
        }
    }

    /**
     * Executes the provided command
     * @param source The source to execute as
     * @param args The arguments given by the source
     * @throws CommandException If there are any exceptions found while parsing the command.
     */
    fun execute(source: T, args: Array<String>) {
        val parseResult = parse(source, args)

        if (parseResult.exception != null) throw CommandException(parseResult.exception)

        parseResult.lastArgument.executor(source, parseResult.parsedArguments)
    }

    /**
     * Returns a list of suggestions for the last argument.
     * @param source The source to suggest as.
     * @param args The arguments given by the source.
     * @return A list of suggestions for the last given argument.
     */
    fun suggestions(source: T, args: Array<String>): List<String> {
        return try {
            parse(source, args).suggestions
        } catch (_: CommandException) {
            emptyList()
        }
    }

    private fun parse(source: T, args: Array<String>): ParseResult {
        val parsedArguments: MutableList<Any> = ArrayList()
        var suggestions: MutableList<String> = ArrayList()

        var currentArgument: CommandArgument<T> = command ?: throw CommandException("Could not find a command executor.")

        if (!currentArgument.requires(source)) throw CommandException("No permission.")

        var exception: String? = null

        args.forEachIndexed { index, argument ->
            // filter requirements
            var newArguments = currentArgument.arguments.filter {
                it.requires(source)
            }

            // Generate suggestions
            if (index == args.lastIndex) {
                suggestions = suggestions.apply {
                    newArguments.forEach { addAll(it.parser.completions() )}
                }.filter {
                    it.contains(argument)
                }.toMutableList()
            }

            // Parse argument
            newArguments = newArguments.filter { it.parser.parse(argument) != null }

            // Return if there are no parsed arguments left.
            if (newArguments.isEmpty()) {
                exception = "Could not parse provided argument"
                return@forEachIndexed
            }

            parsedArguments.add(newArguments[0].parser.parse(argument)!!)

            // Set current argument to next one
            currentArgument = newArguments.first()
        }

        return ParseResult(exception, currentArgument, parsedArguments.toTypedArray(), suggestions)
    }

    data class CommandException(val reason: String) : Exception(reason)

    class ParseResult internal constructor(
        val exception: String?,
        val lastArgument: CommandArgument<*>,
        val parsedArguments: Array<Any>,
        val suggestions: List<String>
    )
}