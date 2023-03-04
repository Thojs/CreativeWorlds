package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class FlagArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("flag")) {
    init {
        if (source is Player) {
            val world = WorldManager.getWorld(source.world)

            world?.flags?.getCommandFlags()?.forEach { flag ->
                argument(LiteralParser(flag.name)) {
                    argument(LiteralParser("set")) {
                        arguments += flag.createArgument(source, world)
                    }

                    argument(LiteralParser("reset")) {
                        executor {
                            world.flags.reset(flag)
                            source.sendMessage(CreativeWorlds.prefix.append(Component.text("Successfully set flag `${flag.name}` to it's default value.")))
                        }
                    }
                }
            }
        }
    }
}