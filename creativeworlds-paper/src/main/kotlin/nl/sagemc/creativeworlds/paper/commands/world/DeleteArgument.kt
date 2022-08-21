package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.commands.WorldCommand
import org.bukkit.command.CommandSender

// TODO
object DeleteArgument : Command.CommandArgument<CommandSender>(LiteralParser("delete")) {
    init {
        require { WorldCommand.testOwner(it) }
    }
}