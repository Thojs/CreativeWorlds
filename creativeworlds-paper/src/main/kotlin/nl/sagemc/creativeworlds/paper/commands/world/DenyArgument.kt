package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.utils.commandhandler.PlayerParser
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// TODO
object DenyArgument : Command.CommandArgument<CommandSender>(LiteralParser("deny")) {
    init {
        require {
            return@require if (it is Player) {
                val world = WorldManager.getWorld(it.world) ?: return@require false
                world.owner == it.uniqueId
            } else {
                true
            }
        }

        argument(PlayerParser) {
            execute { source, arguments ->

            }
        }
    }
}