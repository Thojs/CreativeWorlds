package nl.sagemc.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CreateArgument : Command.CommandArgument<CommandSender>(LiteralParser("create")) {
    init {
        execute { source, _ ->
            if (source !is Player) return@execute
            if (WorldManager.getWorlds(source.uniqueId).isNotEmpty()) {
                source.sendMessage(Component.text("You have reached your world creation limit!"))
                return@execute
            }
            
            WorldManager.createWorld(source.uniqueId)
            source.sendMessage(Component.text("Created a new world!"))
        }
    }
}