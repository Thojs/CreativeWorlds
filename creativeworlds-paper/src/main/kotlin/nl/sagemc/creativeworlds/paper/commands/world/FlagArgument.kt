package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
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
                        appendArgument(flag.createArgument(source, world))
                    }

                    argument(LiteralParser("reset")) {
                        executor {
                            world.flags.reset(flag)
                        }
                    }
                }
            }
        }
    }
}