package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import me.thojs.kommandhandler.core.parsers.StringParser
import net.kyori.adventure.text.Component
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
                world.alias = it[this]
                source.sendMessage(Component.text("Successfully set the alias of this world!"))
            }
        }
    }
}