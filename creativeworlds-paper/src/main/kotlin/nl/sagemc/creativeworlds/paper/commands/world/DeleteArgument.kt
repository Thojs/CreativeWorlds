package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument
import org.bukkit.command.CommandSender

// TODO
class DeleteArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "delete", LiteralParser("delete")) {
    init {
    }
}