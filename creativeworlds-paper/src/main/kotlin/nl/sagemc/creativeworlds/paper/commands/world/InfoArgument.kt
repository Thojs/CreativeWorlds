package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import org.bukkit.command.CommandSender

// TODO
object InfoArgument : Command.CommandArgument<CommandSender>(LiteralParser("info")) {
    init {
        execute { source, arguments ->

        }
    }
}