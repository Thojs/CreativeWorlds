package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.IntegerParser
import nl.sagemc.creativeworlds.paper.utils.commandhandler.PlayerParser
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