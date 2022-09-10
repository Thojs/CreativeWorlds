package nl.sagemc.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// TODO: add flags to info
object InfoArgument : Command.CommandArgument<CommandSender>(LiteralParser("info")) {
    init {
        require {
            return@require if (it is Player) {
                WorldManager.getWorld(it.world) != null
            } else true
        }

        execute { source, _ ->
            if (source !is Player) return@execute

            val world = WorldManager.getWorld(source.world) ?: return@execute

            source.apply {
                sendMessage(Component.text("Owner: ").append(Component.text(Bukkit.getOfflinePlayer(world.owner).name ?: "Unknown")))
                sendMessage(Component.text("Alias: ").append(Component.text(world.alias)))
                sendMessage(Component.text("Trusted: ").append(Component.text(world.trusted.joinToString(", ") { it.name ?: "" })))
                sendMessage(Component.text("Members: ").append(Component.text(world.members.joinToString(", ") { it.name ?: "" })))
                sendMessage(Component.text("Banned: ").append(Component.text(world.denied.joinToString(", ") { it.name ?: "" })))
            }
        }
    }
}