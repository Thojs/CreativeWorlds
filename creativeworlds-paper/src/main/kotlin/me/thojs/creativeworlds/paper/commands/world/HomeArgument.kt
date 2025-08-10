package me.thojs.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.CreativeWorlds
import me.thojs.creativeworlds.paper.commands.BaseCommand
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.PlayerSource
import org.incendo.cloud.paper.util.sender.Source
import org.incendo.cloud.parser.standard.IntegerParser

object HomeArgument : BaseCommand("home") {
    override fun build(builder: MutableCommandBuilder<Source>) {
        builder.registerCopy {
            senderType(PlayerSource::class)
            optional("id", IntegerParser.integerParser())

            handler {
                val player = it.sender().source() as Player
                val id = it.getOrDefault("id", 1)
                goToPlot(player, id)
            }
        }
    }

    private fun goToPlot(player: Player, id: Int) {
        val worlds = WorldManager.getWorlds(player)

        if (id < 1 || worlds.size < id) {
            player.sendMessage(CreativeWorlds.prefix.append(Component.text("Invalid plot id provided. (${worlds.indices.first+1}, ${worlds.indices.last+1})").color(NamedTextColor.RED)))
            return
        }

        val world = worlds[id-1]

        if (world.bukkitWorld == null) {
            player.sendMessage(CreativeWorlds.prefix.append(Component.text("Loading world, you will be teleported when the world has been loaded.").color(NamedTextColor.GREEN)))
        } else {
            player.sendMessage(CreativeWorlds.prefix.append(Component.text("Teleporting you to the world.").color(NamedTextColor.GREEN)))
        }
        world.load()
        world.bukkitWorld?.spawnLocation?.let { player.teleport(it) }
    }
}