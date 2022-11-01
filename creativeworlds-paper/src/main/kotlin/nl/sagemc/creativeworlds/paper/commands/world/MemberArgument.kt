package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import nl.sagemc.creativeworlds.paper.utils.commandhandler.OfflinePlayerParser
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MemberArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "member", LiteralParser("member")) {
    init {
        argument(OfflinePlayerParser id "player") {
            executor {
                if (source !is Player) return@executor

                val world = WorldManager.getWorld(source.world)

                // Add/Remove member
                val player = it[this]

                world?.members?.apply {
                    if (contains(player)) {
                        remove(player)
                        source.sendMessage("Removed ${player.name}")
                    } else {
                        add(player)
                        source.sendMessage("Added ${player.name}")
                    }
                }
            }
        }
    }
}