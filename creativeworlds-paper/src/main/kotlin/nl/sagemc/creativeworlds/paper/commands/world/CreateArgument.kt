package nl.sagemc.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CreateArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "create", LiteralParser("create")) {
    init {
        executor {
            if (source !is Player) return@executor

//            if (WorldManager.getWorlds(source.uniqueId).isNotEmpty()) {
//                source.sendMessage(Component.text("You have reached your world creation limit!"))
//                return@execute
//            }

            source.sendMessage(Component.text("Creating a new world, we will teleport you once the world has been created."))

            val world = WorldManager.createWorld(source)
            world.load()
            world.bukkitWorld?.spawnLocation?.let { source.teleport(it) }
        }
    }
}