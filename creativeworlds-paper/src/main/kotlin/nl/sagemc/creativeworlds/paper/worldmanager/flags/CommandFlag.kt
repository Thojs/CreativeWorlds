package nl.sagemc.creativeworlds.paper.worldmanager.flags

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.IdentifiedParser
import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import org.bukkit.command.CommandSender

abstract class CommandFlag<E : Any>(name: String, defaultValue: E, private val parser: IdentifiedParser<E>) : Flag<E>(name, defaultValue) {
    fun createArgument(source: CommandSender, world: CreativeWorld): CommandArgument<CommandSender, E> {
        return CommandArgument(source, parser).apply {
            executor {
                world.flags[this@CommandFlag] = it[this@apply]
            }
        }
    }
}