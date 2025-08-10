package me.thojs.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.CreativeWorlds
import me.thojs.creativeworlds.paper.commands.BaseCommand
import me.thojs.creativeworlds.paper.commands.exceptions.NotInWorldException
import me.thojs.creativeworlds.paper.worldmanager.Rights
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.incendo.cloud.bukkit.parser.OfflinePlayerParser
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.PlayerSource
import org.incendo.cloud.paper.util.sender.Source

object DenyArgument : BaseCommand("deny") {
    override fun build(builder: MutableCommandBuilder<Source>) {
        builder.registerCopy {
            senderType(PlayerSource::class)
            required("player", OfflinePlayerParser.offlinePlayerParser())

            handler {
                val source = it.sender().source() as Player
                val player = it.get<OfflinePlayer>("player")

                val world = WorldManager.getWorld(source.world) ?: throw NotInWorldException()

                testWorldRights(world, source)

                if (player in world.denied) {
                    world.denied -= player
                    world.updateConfig()
                    source.sendMessage(CreativeWorlds.prefix.append(Component.text("Removed ${player.name} from denied players.", NamedTextColor.GREEN)))
                    return@handler
                }

                if (world.getRights(player) >= Rights.OWNER) {
                    source.sendMessage(CreativeWorlds.prefix.append(Component.text("You are unable to deny ${player.name}.", NamedTextColor.RED)))
                    return@handler
                }

                world.denied += player
                world.trusted -= player
                world.members -= player
                world.updateConfig()
                source.sendMessage(CreativeWorlds.prefix.append(Component.text("Added ${player.name} to denied players.").color(NamedTextColor.GREEN)))

                if (player is Player && player.location.world == world.bukkitWorld) {
                    player.sendMessage(CreativeWorlds.prefix.append(Component.text("You are denied from this plot.").color(NamedTextColor.RED)))
                    player.teleport(Bukkit.getServer().worlds.first().spawnLocation)
                }
            }
        }
    }
}