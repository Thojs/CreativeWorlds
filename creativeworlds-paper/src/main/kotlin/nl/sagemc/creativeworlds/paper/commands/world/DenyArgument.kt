package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument
import nl.sagemc.creativeworlds.paper.utils.commandhandler.PlayerParser
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DenyArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "deny", LiteralParser("deny")) {
    init {
        argument(PlayerParser id "player") {
            executor {
                if (source !is Player) return@executor

                val world = WorldManager.getWorld(source.world)

                val player = it[0] as Player

                world?.denied?.apply {
                    if (contains(player)) {
                        remove(player)
                    } else {
                        add(player)
                    }
                }
            }
        }
    }
}