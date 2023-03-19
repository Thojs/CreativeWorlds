package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.bukkit.parsers.OfflinePlayerParser
import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MemberArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("member")) {
    init {
        argument(OfflinePlayerParser id "player") {
            executor {
                if (source !is Player) return@executor

                val world = WorldManager.getWorld(source.world) ?: return@executor

                // Add/Remove member
                val player = it[this]

                val members = world.members

                if (player in members) {
                    members -= player
                    source.sendMessage(CreativeWorlds.prefix.append(Component.text("Removed ${player.name} from members.").color(NamedTextColor.GREEN)))
                } else {
                    members += player
                    world.trusted -= player
                    world.denied -= player

                    source.sendMessage(CreativeWorlds.prefix.append(Component.text("Added ${player.name} to members.").color(NamedTextColor.GREEN)))
                }
                world.updateConfig()
            }
        }
    }
}