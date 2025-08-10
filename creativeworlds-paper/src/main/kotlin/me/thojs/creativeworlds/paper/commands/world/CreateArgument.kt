package me.thojs.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.CreativeWorlds
import me.thojs.creativeworlds.paper.commands.BaseCommand
import me.thojs.creativeworlds.paper.commands.exceptions.WorldLimitReachedException
import me.thojs.creativeworlds.paper.worldmanager.CreativeWorld
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import me.thojs.creativeworlds.paper.worldmanager.WorldManager.worldLimit
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.PlayerSource
import org.incendo.cloud.paper.util.sender.Source

object CreateArgument : BaseCommand("create") {
    override fun build(builder: MutableCommandBuilder<Source>) {
        builder.registerCopy {
            senderType(PlayerSource::class)

            handler {
                val source = it.sender().source() as Player

                if (WorldManager.getWorlds(source).size >= source.worldLimit) {
                    throw WorldLimitReachedException(source.worldLimit)
                }

                source.sendMessage(CreativeWorlds.prefix.append(Component.text("Creating a new world, you will be teleported when the world has been loaded.").color(NamedTextColor.GREEN)))
                val spawnLocation = createWorld(source).bukkitWorld?.spawnLocation ?: return@handler
                source.teleportAsync(spawnLocation)
            }
        }
    }

    fun createWorld(player: Player): CreativeWorld {
        val world = WorldManager.createWorld(player)
        world.load()
        return world
    }
}
