package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetSpawnArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "setspawn", LiteralParser("setspawn")) {
    init {
        executor {
            if (source !is Player) return@executor

            val world = WorldManager.getWorld(source.world)

            world?.bukkitWorld?.spawnLocation = source.location
        }
    }
}