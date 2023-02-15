package nl.sagemc.creativeworlds.paper.commands

import me.thojs.kommandhandler.core.CommandCreator
import nl.sagemc.creativeworlds.paper.commands.world.*
import nl.sagemc.creativeworlds.paper.worldmanager.Rights
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object WorldCommand : CommandCreator<CommandSender>() {
    init {
        command("world", "w", "plot", "p") {
            appendArgument(CreateArgument(source))
            appendArgument(HomeArgument(source))
            appendArgument(VisitArgument(source))

            if (source is Player && WorldManager.getWorld((source as Player).world) != null) {
                appendArgument(InfoArgument(source))
            }

            if (testOwner(source)) {
                source.apply {
                    appendArgument(TrustArgument(this))
                    appendArgument(MemberArgument(this))
                    appendArgument(FlagArgument(this)) // TODO FIX
                    appendArgument(DenyArgument(this))
                    appendArgument(AliasArgument(this))
                    appendArgument(DeleteArgument(this)) // TODO
                    appendArgument(SetSpawnArgument(this))
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
