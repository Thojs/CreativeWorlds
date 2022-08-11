package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.StringParser
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object AliasArgument : Command.CommandArgument<CommandSender>(LiteralParser("alias")) {
    init {
        argument(StringParser) {
            execute { source, arguments ->
                if (source !is Player) return@execute

                val world = WorldManager.getWorld(source.world)

                // Check if source is owner of world
                if (world?.owner?.equals(source) != true) return@execute

                // Set alias
                val alias = arguments[1] as String
                world.alias = alias
            }
        }
    }
}