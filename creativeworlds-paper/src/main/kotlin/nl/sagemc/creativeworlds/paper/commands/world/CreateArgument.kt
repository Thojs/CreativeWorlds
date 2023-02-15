package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager.worldLimit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CreateArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("create")) {
    init {
        executor {
            if (source !is Player) return@executor

            if (WorldManager.getWorlds(source).size >= source.worldLimit) {
                source.sendMessage(CreativeWorlds.prefix.append(Component.text("You have reached your world creation limit of ${source.worldLimit}!").color(NamedTextColor.RED)))
                return@executor
            }

            source.sendMessage(CreativeWorlds.prefix.append(Component.text("Creating a new world, we will teleport you when the world has been loaded.").color(NamedTextColor.GREEN)))

            val world = WorldManager.createWorld(source)
            world.load()
            world.bukkitWorld?.spawnLocation?.let { source.teleport(it) }
        }
    }
}
