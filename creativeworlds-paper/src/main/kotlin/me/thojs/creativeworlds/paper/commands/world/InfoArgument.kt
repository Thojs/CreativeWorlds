package me.thojs.creativeworlds.paper.commands.world

import me.thojs.creativeworlds.paper.commands.BaseCommand
import me.thojs.creativeworlds.paper.commands.exceptions.NotInWorldException
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.utils.Utils.miniMessage
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.PlayerSource
import org.incendo.cloud.paper.util.sender.Source

object InfoArgument : BaseCommand("info") {
    override fun build(builder: MutableCommandBuilder<Source>) {
        builder.registerCopy {
            senderType(PlayerSource::class)

            handler {
                val source = it.sender().source() as Player
                val world = WorldManager.getWorld(source.world) ?: throw NotInWorldException()

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
    }

    private fun addTag(tag: String, msg: String?): Component {
        return miniMessage("<yellow>$tag</yellow><dark_gray>: <reset>").append(Component.text(msg ?: "").color(NamedTextColor.GRAY))
    }
}