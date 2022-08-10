package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.IntegerParser
import nl.sagemc.creativeworlds.paper.utils.commandhandler.PlayerParser
import org.bukkit.command.CommandSender

// TODO
object VisitArgument : Command.CommandArgument<CommandSender>(LiteralParser("visit")) {
    init {
        argument(PlayerParser) {
            argument(IntegerParser) {
                execute { source, arguments ->

                }
            }

            execute { source, arguments ->

            }
        }
    }
}