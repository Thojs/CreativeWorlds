package nl.sagemc.creativeworlds.api.commandhandler

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.StringParser
import nl.sagemc.creativeworlds.api.commandhandler.exceptions.ArgumentParseException
import nl.sagemc.creativeworlds.api.commandhandler.exceptions.CommandException

open class Command<S>(val name: String, vararg val aliases: String) {
    var command: CommandArgument<S, String>.() -> Unit = {}
        private set

    fun command(command: CommandArgument<S, String>.() -> Unit) {
        this.command = command
    }

    /**
     * Executes the provided command
     * @param source The source to execute as
     * @param args The arguments given by the source
     * @throws CommandException If there are any exceptions found while parsing the command.
     */
    fun execute(source: S, args: Array<String>) {
        val parseResult = parse(source, args)

        if (parseResult.exception != null) throw parseResult.exception

        parseResult.lastArgument.executor?.invoke(parseResult.parsedArguments)
    }

    /**
     * Returns a list of suggestions for the last argument.
     * @param source The source to suggest as.
     * @param args The arguments given by the source.
     * @return A list of suggestions for the last given argument.
     */
    fun suggestions(source: S, args: Array<String>): List<String> {
        return try {
            parse(source, args).suggestions
        } catch (_: CommandException) {
            emptyList()
        }
    }

    //TODO
    /**
     * Returns a usage string with the given arguments.
     * @param source The source to generate a usage string for.
     * @param args The arguments given by the source.
     * @return A usage string.
     * @throws NotImplementedError FUNCTION IS NOT IMPLEMENTED YET
     */
    fun usage(source: S, args: Array<String>) {
        throw NotImplementedError()
    }

    private fun parse(source: S, args: Array<String>): ParseResult<S> {
        val parsedArguments: MutableList<Pair<CommandArgument<S, *>, Any>> = ArrayList()

        var suggestions: MutableList<String> = ArrayList()
        var currentArgument: CommandArgument<S, *> = CommandArgument(source, "root", StringParser).apply(command)

        parsedArguments.add(Pair(currentArgument, name))

        var exception: CommandException? = null

        args.forEachIndexed { index, argument ->
            // filter requirements
            var newArguments = currentArgument.arguments.toList()

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
                exception = ArgumentParseException(currentArgument)
                return@forEachIndexed
            }

            parsedArguments.add(Pair(newArguments[0], newArguments[0].parser.parse(argument)!!))

            // Set current argument to next one
            currentArgument = newArguments.first()
        }

        return ParseResult(exception, currentArgument, Arguments(parsedArguments), suggestions)
    }

    class ParseResult<S> internal constructor(
        val exception: CommandException?,
        val lastArgument: CommandArgument<S, *>,
        val parsedArguments: Arguments,
        val suggestions: List<String>
    )
}