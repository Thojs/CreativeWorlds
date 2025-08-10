package me.thojs.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.CreativeWorlds
import me.thojs.creativeworlds.paper.commands.BaseCommand
import me.thojs.creativeworlds.paper.commands.exceptions.NotInWorldException
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.PlayerSource
import org.incendo.cloud.paper.util.sender.Source
import org.incendo.cloud.parser.standard.StringParser

object AliasArgument : BaseCommand("alias") {
    override fun build(builder: MutableCommandBuilder<Source>) {
        builder.registerCopy {
            senderType(PlayerSource::class)
            literal("set")
            required("alias", StringParser.greedyStringParser())

            handler {
                val source = it.sender().source() as Player
                val world = WorldManager.getWorld(source.world) ?: throw NotInWorldException()

                testWorldRights(world, source)

                val alias = it.get<String>("alias")
                world.alias = alias
                source.sendMessage(CreativeWorlds.prefix.append(Component.text("Successfully set the alias of this world!", NamedTextColor.GREEN)))
            }
        }

        builder.registerCopy {
            senderType(PlayerSource::class)
            literal("reset")

            handler {
                val source = it.sender().source() as Player
                val world = WorldManager.getWorld(source.world) ?: throw NotInWorldException()

                testWorldRights(world, source)

                world.alias = null
                source.sendMessage(CreativeWorlds.prefix.append(Component.text("Successfully reset the alias of this world!", NamedTextColor.GREEN)))
            }
        }
    }
}