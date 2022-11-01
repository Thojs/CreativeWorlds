package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import org.bukkit.command.CommandSender

// TODO
class FlagArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "flag", LiteralParser("flag")) {
    init {
        // TODO: Add FlagParser
        argument(LiteralParser("set") id "set") {

        }
    }
}