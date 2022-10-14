package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.paper.utils.commandhandler.PlayerParser
import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TrustArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "trust", LiteralParser("trust")) {
    init {
        argument(PlayerParser id "player") {
            executor {
                if (source !is Player) return@executor

                val world = WorldManager.getWorld(source.world)

                // Add/Remove Trusted player
                val player = it[this]

                world?.trusted?.apply {
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