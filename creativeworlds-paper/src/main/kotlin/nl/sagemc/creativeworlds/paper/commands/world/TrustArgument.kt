package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.paper.utils.commandhandler.PlayerParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import org.bukkit.command.CommandSender

// TODO
object TrustArgument : Command.CommandArgument<CommandSender>(LiteralParser("trust")) {
    init {
        argument(PlayerParser) {
            execute { source, arguments ->

            }
        }
    }
}