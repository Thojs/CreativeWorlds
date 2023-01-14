package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import org.bukkit.command.CommandSender

// TODO
class DeleteArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "delete", LiteralParser("delete")) {
    init {
        executor {
            source.sendMessage(CreativeWorlds.prefix.append(Component.text("This sub-command is not implemented yet!").color(NamedTextColor.RED)))
        }
    }
}