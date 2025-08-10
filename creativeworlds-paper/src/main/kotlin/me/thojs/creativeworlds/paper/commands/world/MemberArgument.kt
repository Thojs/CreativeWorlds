package me.thojs.creativeworlds.paper.commands.world

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.CreativeWorlds
import me.thojs.creativeworlds.paper.commands.BaseCommand
import me.thojs.creativeworlds.paper.commands.exceptions.NotInWorldException
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.entity.Player
import org.incendo.cloud.bukkit.parser.OfflinePlayerParser
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.PlayerSource
import org.incendo.cloud.paper.util.sender.Source

object MemberArgument : BaseCommand("members") {
    override fun build(builder: MutableCommandBuilder<Source>) {
        builder.registerCopy {
            literal("list")

            handler {
                //TODO
            }
        }

        builder.registerCopy {
            senderType(PlayerSource::class)
            literal("add")
            required("player", OfflinePlayerParser.offlinePlayerParser())
            flag("trust")

            handler {
                val source = it.sender().source() as Player
                val world = WorldManager.getWorld(source.world) ?: throw NotInWorldException()

                testWorldRights(world, source)

                val newMember = it.get<Player>("player")
                val shouldTrust = it.flags().hasFlag("trust")

                val list = if (shouldTrust) world.trusted else world.members
                if (newMember in list) return@handler

                list += newMember
                (if (shouldTrust) world.members else world.trusted) -= newMember

                source.sendMessage(CreativeWorlds.prefix.append(Component.text("Added ${newMember.name} to ${if (shouldTrust) "trusted" else "members"}.", NamedTextColor.GREEN)))
            }
        }

        builder.registerCopy {
            literal("remove")
            //TODO
        }
    }
}