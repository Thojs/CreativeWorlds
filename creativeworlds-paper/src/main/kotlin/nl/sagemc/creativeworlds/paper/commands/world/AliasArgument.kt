package nl.sagemc.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.StringParser
import nl.sagemc.creativeworlds.paper.commands.WorldCommand
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object AliasArgument : Command.CommandArgument<CommandSender>(LiteralParser("alias")) {
    init {
        require { WorldCommand.testOwner(it) }

        argument(StringParser) {
            execute { source, arguments ->
                if (source !is Player) return@execute

                val world = WorldManager.getWorld(source.world)

                // Check if source is owner of world
                if (world?.owner?.equals(source) != true) return@execute

                // Set alias
                val alias = arguments[1] as String
                world.alias = alias
                source.sendMessage(Component.text("Successfully set the alias of this world!"))
            }
        }
    }
}