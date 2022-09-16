package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument
import nl.sagemc.creativeworlds.paper.utils.commandhandler.PlayerParser
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MemberArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "member", LiteralParser("member")) {
    init {
        argument(PlayerParser id "player") {
            executor {
                if (source !is Player) return@executor

                val world = WorldManager.getWorld(source.world)

                // Check if source is owner of world
                if (world?.owner?.equals(source) != true) return@executor

                // Add/Remove member
                val player = it[1] as Player

                val members = world.members
                if (members.contains(player)) {
                    members.remove(player)
                } else {
                    members.add(player)
                }
            }
        }
    }
}