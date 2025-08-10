package me.thojs.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.CreativeWorlds
import me.thojs.creativeworlds.paper.commands.BaseCommand
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.incendo.cloud.bukkit.parser.OfflinePlayerParser
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.PlayerSource
import org.incendo.cloud.paper.util.sender.Source
import org.incendo.cloud.parser.standard.IntegerParser

object VisitArgument : BaseCommand("visit") {
    override fun build(builder: MutableCommandBuilder<Source>) {
        builder.registerCopy {
            senderType(PlayerSource::class)
            required("target", OfflinePlayerParser.offlinePlayerParser())
            optional("id", IntegerParser.integerParser())

            handler {
                val source = it.sender().source() as Player
                val target = it.get<OfflinePlayer>("target")
                val id = it.getOrDefault("id", 1)

                visitWorld(source, target, id)
            }
        }
    }

     fun visitWorld(visitor: Player, player: OfflinePlayer, id: Int) {
         val worlds = WorldManager.getWorlds(player)

         if (worlds.isEmpty()) {
             visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("No worlds found from ${player.name}.", NamedTextColor.RED)))
             return
         }

         if (id < 0 || worlds.size < id) {
             visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("Invalid plot id provided. (${worlds.indices.first+1}, ${worlds.indices.last+1})", NamedTextColor.RED)))
             return
         }

         val world = worlds[id-1]

         if (world.denied.contains(visitor) && (!player.isOp || world.owner != visitor)) {
             visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("You are denied from this plot.", NamedTextColor.RED)))
             return
         }

         if (world.bukkitWorld == null) {
             visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("Loading world, you will be teleported when the world has been loaded.", NamedTextColor.GREEN)))
         } else {
             visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("Teleporting you to the world.", NamedTextColor.GREEN)))
         }

         world.load()
         world.bukkitWorld?.spawnLocation?.let { visitor.teleport(it) }
     }
}