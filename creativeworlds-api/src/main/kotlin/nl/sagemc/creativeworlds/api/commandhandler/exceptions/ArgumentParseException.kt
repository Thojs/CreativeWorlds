package nl.sagemc.creativeworlds.api.commandhandler.exceptions

import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument

class ArgumentParseException(argument: CommandArgument<*, *>) : CommandException("Could not parse the provided argument.")