package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.commands.WorldCommand
import nl.sagemc.creativeworlds.paper.utils.commandhandler.PlayerParser
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// TODO
object MemberArgument : Command.CommandArgument<CommandSender>(LiteralParser("member")) {
    init {
        require { WorldCommand.testOwner(it) }

        argument(PlayerParser) {
            execute { source, arguments ->
                if (source !is Player) return@execute

                val world = WorldManager.getWorld(source.world)

                // Check if source is owner of world
                if (world?.owner?.equals(source) != true) return@execute

                // Set alias
                val player = arguments[1] as Player

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