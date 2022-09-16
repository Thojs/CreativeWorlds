package nl.sagemc.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.StringParser
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AliasArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "alias", LiteralParser("alias")) {
    init {
        argument(StringParser id "alias") {
            executor {
                if (source !is Player) return@executor

                val world = WorldManager.getWorld(source.world)

                // Check if source is owner of world
                if (world?.owner?.equals(source) != true) return@executor

                // Set alias
                val alias = it[1] as String
                world.alias = alias
                source.sendMessage(Component.text("Successfully set the alias of this world!"))
            }
        }
    }
}