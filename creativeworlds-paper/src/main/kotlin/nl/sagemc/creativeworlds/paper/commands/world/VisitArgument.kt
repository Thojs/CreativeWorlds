package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.bukkit.parsers.OfflinePlayerParser
import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.IntegerParser
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VisitArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("visit")) {
    init {
        argument(OfflinePlayerParser id "target") {
            val target = this
            argument(IntegerParser id "id") {
                executor {
                    if (source !is Player) return@executor

                    val player = it[target]
                    val id = it[this]

                    visitWorld(source, player, id)
                }
            }

            executor {
                if (source !is Player) return@executor

                val player = it[this] ?: return@executor

                visitWorld(source, player, 1)
            }
        }
    }

     companion object {
         fun visitWorld(visitor: Player, player: OfflinePlayer, id: Int) {
             val worlds = WorldManager.getWorlds(player)

             if (worlds.isEmpty()) {
                 visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("No worlds found from ${player.name}.").color(NamedTextColor.RED)))
                 return
             }

             if (id < 0 || worlds.size < id) {
                 visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("Invalid plot id provided. (${worlds.indices.first+1}, ${worlds.indices.last+1})").color(NamedTextColor.RED)))
                 return
             }

             val world = worlds[id-1]

             if (world.denied.contains(visitor) && (!player.isOp || world.owner != visitor)) {
                 visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("You are denied from this plot.").color(NamedTextColor.RED)))
                 return
             }

             if (world.bukkitWorld == null) {
                 visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("Loading world, we will teleport you when the world has been loaded.").color(NamedTextColor.GREEN)))
             } else {
                 visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("Teleporting you to the world.").color(NamedTextColor.GREEN)))
             }

             world.load()
             world.bukkitWorld?.spawnLocation?.let { visitor.teleport(it) }
         }
     }
}