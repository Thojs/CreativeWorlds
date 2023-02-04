package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.bukkit.parsers.PlayerParser
import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DenyArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("deny")) {
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
                        if (player.location.world == world.bukkitWorld) {
                            player.sendMessage(CreativeWorlds.prefix.append(Component.text("You are denied from this plot.").color(NamedTextColor.RED)))
                            player.teleport(Bukkit.getServer().worlds.first().spawnLocation)
                        }
                    }
                }
            }
        }
    }
}