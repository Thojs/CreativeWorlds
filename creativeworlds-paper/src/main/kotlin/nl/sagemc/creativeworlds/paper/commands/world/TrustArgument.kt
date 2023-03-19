package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.bukkit.parsers.OfflinePlayerParser
import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TrustArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("trust")) {
    init {
        argument(OfflinePlayerParser id "player") {
            executor {
                if (source !is Player) return@executor

                val world = WorldManager.getWorld(source.world) ?: return@executor

                // Add/Remove Trusted player
                val player = it[this]

                val trusted = world.trusted

                if (player in trusted) {
                    trusted -= player
                    source.sendMessage(CreativeWorlds.prefix.append(Component.text("Removed ${player.name} from trusted.").color(NamedTextColor.GREEN)))
                } else {
                    trusted += player
                    world.members -= player
                    world.denied -= player

                    source.sendMessage(CreativeWorlds.prefix.append(Component.text("Added ${player.name} to trusted.").color(NamedTextColor.GREEN)))
                }

                world.updateConfig()
            }
        }
    }
}