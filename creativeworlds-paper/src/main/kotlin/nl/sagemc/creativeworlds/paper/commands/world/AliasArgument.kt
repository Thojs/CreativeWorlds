package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import me.thojs.kommandhandler.core.parsers.StringParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AliasArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("alias")) {
    init {
        argument(LiteralParser("reset")) {
            executor {
                if (source !is Player) return@executor

                val world = WorldManager.getWorld(source.world)
                world?.alias = null
                source.sendMessage(CreativeWorlds.prefix.append(Component.text("Successfully reset the alias of this world!").color(NamedTextColor.GREEN)))
            }
        }
        argument(LiteralParser("set")) {
            argument(StringParser id "alias") {
                executor {
                    if (source !is Player) return@executor

                    val world = WorldManager.getWorld(source.world)

                    // Set alias
                    world?.alias = it[this]
                    source.sendMessage(
                        CreativeWorlds.prefix.append(
                            Component.text("Successfully set the alias of this world!").color(NamedTextColor.GREEN)
                        )
                    )
                }
            }
        }
    }
}