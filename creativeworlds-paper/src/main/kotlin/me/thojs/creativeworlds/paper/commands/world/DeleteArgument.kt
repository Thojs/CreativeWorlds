package me.thojs.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.CreativeWorlds
import org.bukkit.command.CommandSender

// TODO
class DeleteArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("delete")) {
    init {
        executor {
            source.sendMessage(me.thojs.creativeworlds.paper.CreativeWorlds.prefix.append(Component.text("This sub-command is not implemented yet!").color(NamedTextColor.RED)))
        }
    }
}