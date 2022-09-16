package nl.sagemc.creativeworlds.paper.commands

import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.commands.world.*
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object WorldCommand : Command<CommandSender>("world", "w", "plot", "p") {
    init {
        command {
            appendArgument(CreateArgument(source)) // DONE
            appendArgument(HomeArgument(source)) // DONE
            appendArgument(VisitArgument(source))

            if (source is Player && WorldManager.getWorld((source as Player).world) != null) {
                appendArgument(InfoArgument(source))
            }

            if (testOwner(source)) {
                source.apply {
                    appendArgument(TrustArgument(this)) // DONE
                    appendArgument(MemberArgument(this)) // DONE
                    appendArgument(FlagArgument(this))
                    appendArgument(DenyArgument(this))
                    appendArgument(AliasArgument(this)) // DONE
                    appendArgument(DeleteArgument(this))
                    appendArgument(SetSpawnArgument(this)) // DONE
                }
            }
        }
    }

    private fun testOwner(source: CommandSender): Boolean {
        return if (source is Player) {
            val world = WorldManager.getWorld(source.world) ?: return false
            world.owner == source
        } else {
            true
        }
    }
}