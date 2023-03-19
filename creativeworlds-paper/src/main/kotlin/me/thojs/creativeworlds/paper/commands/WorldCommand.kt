package me.thojs.creativeworlds.paper.commands

import me.thojs.kommandhandler.core.CommandCreator
import me.thojs.creativeworlds.paper.commands.world.*
import me.thojs.creativeworlds.paper.worldmanager.Rights
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object WorldCommand : CommandCreator<CommandSender>() {
    init {
        command("world", "w", "plot", "p") {
            arguments.addAll(listOf(
                CreateArgument(source),
                HomeArgument(source),
                VisitArgument(source)
            ))

            if (source is Player && WorldManager.getWorld((source as Player).world) != null) {
                arguments += InfoArgument(source)
            }

            if (me.thojs.creativeworlds.paper.commands.WorldCommand.testOwner(source)) {
                source.apply {
                    arguments.addAll(listOf(
                        TrustArgument(this),
                        MemberArgument(this),
                        FlagArgument(this),
                        DenyArgument(this),
                        AliasArgument(this),
                        DeleteArgument(this), // TODO
                        SetSpawnArgument(this)
                    ))
                }

                if (source.isOp) {
                    arguments += UnloadArgument(source)
                }
            }
        }
    }

    private fun testOwner(source: CommandSender): Boolean {
        return if (source is Player) {
            val world = WorldManager.getWorld(source.world) ?: return false
            return world.getRights(source) >= Rights.OWNER
        } else false
    }
}
