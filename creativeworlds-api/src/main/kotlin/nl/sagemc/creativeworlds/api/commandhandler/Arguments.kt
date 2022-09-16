package nl.sagemc.creativeworlds.api.commandhandler

class Arguments(private val arguments: List<Pair<CommandArgument<*, *>, Any>>) {

    /**
     * Function to access an argument value.
     * @param argument The argument to get the value from.
     * @throws IllegalArgumentException If the provided argument is not found.
     * @return The value from the given argument.
     */
    operator fun <E> get(argument: CommandArgument<*, E>): E {
        val foundArgument = arguments.find { it.first == argument } ?: throw IllegalArgumentException("Unknown argument provided.")

        @Suppress("UNCHECKED_CAST")
        return foundArgument.second as E
    }

    operator fun get(index: Int): Any {
        return arguments[index].second
    }

    operator fun get(identifier: String): Any? {
        return arguments.find { it.first.identifier == identifier }?.second
    }

    fun asList(): List<Any> {
        return arguments.map { it.second }
    }
}