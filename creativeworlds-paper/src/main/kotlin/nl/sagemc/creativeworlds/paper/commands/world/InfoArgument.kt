package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// TODO: add flags to info
class InfoArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "info", LiteralParser("info")) {
    init {
        executor {
            if (source !is Player) return@executor

            val world = WorldManager.getWorld(source.world) ?: return@executor

            source.apply {
                sendMessage(Component.text("Owner: ").append(Component.text(world.owner.name ?: "Unknown")))
                sendMessage(Component.text("Alias: ").append(Component.text(world.alias)))
                sendMessage(Component.text("Trusted: ").append(Component.text(world.trusted.joinToString(", ") { it.name ?: "" })))
                sendMessage(Component.text("Members: ").append(Component.text(world.members.joinToString(", ") { it.name ?: "" })))
                sendMessage(Component.text("Banned: ").append(Component.text(world.denied.joinToString(", ") { it.name ?: "" })))
            }
        }
    }
}