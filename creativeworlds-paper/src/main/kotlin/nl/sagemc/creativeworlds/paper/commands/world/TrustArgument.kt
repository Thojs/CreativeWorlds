package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import nl.sagemc.creativeworlds.paper.utils.commandhandler.OfflinePlayerParser
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TrustArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "trust", LiteralParser("trust")) {
    init {
        argument(OfflinePlayerParser id "player") {
            executor {
                if (source !is Player) return@executor

                val world = WorldManager.getWorld(source.world)

                // Add/Remove Trusted player
                val player = it[this]

                world?.trusted?.apply {
                    if (contains(player)) {
                        remove(player)
                        source.sendMessage("Untrusted ${player.name}")
                    } else {
                        add(player)
                        source.sendMessage("Trusted ${player.name}")
                    }
                }
            }
        }
    }
}