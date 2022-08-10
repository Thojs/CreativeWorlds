package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.IntegerParser
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// TODO
object HomeArgument : Command.CommandArgument<CommandSender>(LiteralParser("home")) {
    init {
        // (plot-id)
        argument(IntegerParser) {
            execute { source, arguments ->

            }
        }

        execute { source, arguments ->

        }
    }

    fun goToPlot(player: Player, id: Int) {

    }
}