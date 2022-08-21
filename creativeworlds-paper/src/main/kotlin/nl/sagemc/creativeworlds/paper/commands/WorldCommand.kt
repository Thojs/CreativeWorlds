package nl.sagemc.creativeworlds.paper.commands

import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.commands.world.*
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object WorldCommand : Command<CommandSender>("world", "w", "plot", "p") {
    init {
        command {
            appendArguments(
                CreateArgument, // DONE
                HomeArgument, // DONE
                InfoArgument,
                TrustArgument, // DONE
                MemberArgument, // DONE
                FlagArgument,
                DenyArgument,
                AliasArgument, // DONE
                VisitArgument,
                DeleteArgument,
                SetSpawnArgument // DONE
            )
        }
    }

    fun testOwner(source: CommandSender): Boolean {
        return if (source is Player) {
            val world = WorldManager.getWorld(source.world) ?: return false
            world.owner == source.uniqueId
        } else {
            true
        }
    }
}