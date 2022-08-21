package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.commands.WorldCommand
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// TODO
object FlagArgument : Command.CommandArgument<CommandSender>(LiteralParser("flag")) {
    init {
        require { WorldCommand.testOwner(it) }

        // TODO: Add FlagParser
        argument(LiteralParser("set")) {

        }

        // TODO: Add FlagParser
        argument(LiteralParser("remove")) {

        }
    }
}