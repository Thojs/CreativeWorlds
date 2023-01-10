package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.bukkit.parsers.PlayerParser
import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.IntegerParser
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender

// TODO
class VisitArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "visit", LiteralParser("visit")) {
    init {
        argument(PlayerParser id "target") {
            argument(IntegerParser id "id") {
                executor {

                }
            }

            executor {
                val worlds = WorldManager.getWorlds(it[this] ?: return@executor)

                if (worlds.isEmpty()) {
                    source.sendMessage(CreativeWorlds.prefix.append(Component.text("No worlds found for '${it[this]?.name}'").color(NamedTextColor.RED)))
                    return@executor
                }
            }
        }
    }
}