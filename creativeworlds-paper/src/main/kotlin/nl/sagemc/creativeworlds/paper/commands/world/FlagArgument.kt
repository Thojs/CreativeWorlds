package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import org.bukkit.command.CommandSender

// TODO
object FlagArgument : Command.CommandArgument<CommandSender>(LiteralParser("flag")) {
    init {
        arguments(LiteralParser("set"), LiteralParser("FLAGPARSER HERE"), LiteralParser("FLAGPARSER VALUE HERE")) {

        }

        // TODO: Add FlagParser
        arguments(LiteralParser("remove")) {

        }
    }
}