package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.paper.utils.commandhandler.PlayerParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object TrustArgument : Command.CommandArgument<CommandSender>(LiteralParser("trust")) {
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
                if (source !is Player) return@execute

                val world = WorldManager.getWorld(source.world)

                // Check if source is owner of world
                if (world?.owner?.equals(source) != true) return@execute

                // Set alias
                val player = arguments[1] as Player

                val trusted = world.trusted
                if (trusted.contains(player)) {
                    trusted.remove(player)
                } else {
                    trusted.add(player)
                }
            }
        }
    }
}