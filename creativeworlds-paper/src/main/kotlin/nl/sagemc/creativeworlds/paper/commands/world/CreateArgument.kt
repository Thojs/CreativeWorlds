package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// TODO
object CreateArgument : Command.CommandArgument<CommandSender>(LiteralParser("create")) {
    init {
        execute { source, _ ->
            if (source !is Player) return@execute
            if (WorldManager.getWorlds(source).isNotEmpty()) {
                // TODO: Say world limit reached!
                return@execute
            }
            WorldManager.createWorld(source)
            // TODO: Say world created!
        }
    }
}