package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.bukkit.parsers.PlayerParser
import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.IntegerParser
import me.thojs.kommandhandler.core.parsers.LiteralParser
import org.bukkit.command.CommandSender

// TODO
class VisitArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "visit", LiteralParser("visit")) {
    init {
        argument(PlayerParser id "target") {
            argument(IntegerParser id "id") {
                executor {

                }
            }

            executor {

            }
        }
    }
}