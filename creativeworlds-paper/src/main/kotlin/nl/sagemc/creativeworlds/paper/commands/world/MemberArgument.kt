package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.utils.commandhandler.PlayerParser
import org.bukkit.command.CommandSender

// TODO
object MemberArgument : Command.CommandArgument<CommandSender>(LiteralParser("member")) {
    init {
        TrustArgument.argument(PlayerParser) {
            execute { source, arguments ->

            }
        }
    }
}