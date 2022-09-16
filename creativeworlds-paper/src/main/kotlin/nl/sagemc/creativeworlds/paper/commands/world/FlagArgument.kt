package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument
import org.bukkit.command.CommandSender

// TODO
class FlagArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "flag", LiteralParser("flag")) {
    init {
        // TODO: Add FlagParser
        argument(LiteralParser("set") id "set") {
            return@argument

        }

        // TODO: Add FlagParser
        argument(LiteralParser("remove") id "remove") {

        }
    }
}