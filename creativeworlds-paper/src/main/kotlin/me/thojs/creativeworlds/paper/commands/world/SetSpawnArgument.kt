package me.thojs.creativeworlds.paper.commands.world

import me.thojs.creativeworlds.paper.commands.BaseCommand
import me.thojs.creativeworlds.paper.commands.exceptions.NotInWorldException
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.PlayerSource
import org.incendo.cloud.paper.util.sender.Source

object SetSpawnArgument : BaseCommand("setspawn") {
    override fun build(builder: MutableCommandBuilder<Source>) {
        builder.registerCopy {
            senderType(PlayerSource::class)

            handler {
                val source = it.sender().source() as Player
                val world = WorldManager.getWorld(source.world) ?: throw NotInWorldException()

                testWorldRights(world, source)

                world.bukkitWorld?.spawnLocation = source.location
            }
        }
    }
}