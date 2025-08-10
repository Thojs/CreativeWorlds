package me.thojs.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.CreativeWorlds
import me.thojs.creativeworlds.paper.commands.BaseCommand
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.Source

// TODO
object DeleteArgument : BaseCommand("delete") {
    override fun build(builder: MutableCommandBuilder<Source>) {
        builder.registerCopy {
            handler {
                it.sender().source().sendMessage(CreativeWorlds.prefix.append(Component.text("This sub-command is not implemented yet!", NamedTextColor.RED)))
            }
        }
    }
}