package me.thojs.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.utils.Utils.miniMessage
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class InfoArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("info", "i")) {
    init {
        executor {
            if (source !is Player) return@executor

            val world = WorldManager.getWorld(source.world) ?: return@executor

            source.apply {
                val title = miniMessage("<dark_gray><strikethrough>--------[</strikethrough> <gold>World</gold><yellow>Info</yellow> <strikethrough>]--------")
                sendMessage(title)
                sendMessage(addTag("Owner", world.owner.name ?: "Unknown"))
                sendMessage(addTag("Alias", world.alias))
                sendMessage(addTag("Trusted", world.trusted.toString()))
                sendMessage(addTag("Members", world.members.toString()))
                sendMessage(addTag("Denied", world.denied.toString()))
                sendMessage(title)
            }
        }
    }

    private fun addTag(tag: String, msg: String?): Component {
        return miniMessage("<yellow>$tag</yellow><dark_gray>: <reset>").append(Component.text(msg ?: "").color(NamedTextColor.GRAY))
    }
}