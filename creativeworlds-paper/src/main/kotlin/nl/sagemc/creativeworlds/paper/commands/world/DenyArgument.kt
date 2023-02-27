package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.bukkit.parsers.OfflinePlayerParser
import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.Rights
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DenyArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("deny")) {
    init {
        argument(OfflinePlayerParser id "player") {
            executor {
                if (source !is Player) return@executor

                val world = WorldManager.getWorld(source.world)

                val player = it[this]

                world?.denied?.apply {
                    if (contains(player)) {
                        remove(player)
                        source.sendMessage(CreativeWorlds.prefix.append(Component.text("Removed ${player.name} from denied players.").color(NamedTextColor.GREEN)))
                    } else {
                        if (world.getRights(player) >= Rights.OWNER) {
                            source.sendMessage(CreativeWorlds.prefix.append(Component.text("You are unable to ban ${player.name}.").color(NamedTextColor.RED)))
                            return@apply
                        }
                        add(player)
                        if (player is Player && player.location.world == world.bukkitWorld) {
                            player.sendMessage(CreativeWorlds.prefix.append(Component.text("You are denied from this plot.").color(NamedTextColor.RED)))
                            player.teleport(Bukkit.getServer().worlds.first().spawnLocation)
                        }
                        source.sendMessage(CreativeWorlds.prefix.append(Component.text("Added ${player.name} to denied players.").color(NamedTextColor.GREEN)))
                    }
                    world.updateConfig()
                }
            }
        }
    }
}