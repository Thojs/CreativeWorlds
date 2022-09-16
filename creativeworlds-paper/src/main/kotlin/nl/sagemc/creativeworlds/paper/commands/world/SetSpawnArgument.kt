package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetSpawnArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "setspawn", LiteralParser("setspawn")) {
    init {
        executor {
            if (source !is Player) return@executor

            val world = WorldManager.getWorld(source.world)

            // Check if source is owner of world
            if (world?.owner?.equals(source) != true) return@executor

            world.bukkitWorld?.spawnLocation = source.location
        }
    }
}