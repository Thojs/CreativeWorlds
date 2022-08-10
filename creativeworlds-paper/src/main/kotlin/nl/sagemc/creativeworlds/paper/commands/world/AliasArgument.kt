package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.StringParser
import org.bukkit.command.CommandSender

// TODO
object AliasArgument : Command.CommandArgument<CommandSender>(LiteralParser("alias")) {
    init {
        argument(StringParser) {
            execute { source, arguments ->

            }
        }
    }
}