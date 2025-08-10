package me.thojs.creativeworlds.paper.commands.exceptions

import me.thojs.creativeworlds.paper.CreativeWorlds
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.incendo.cloud.exception.handling.ExceptionContext
import org.incendo.cloud.exception.handling.ExceptionHandler
import org.incendo.cloud.paper.util.sender.Source

object CreativeWorldsExceptionHandler : ExceptionHandler<Source, CreativeWorldsException> {
    override fun handle(context: ExceptionContext<Source, CreativeWorldsException>) {
        val source = context.context().sender().source()
        val exception = context.exception()

        source.sendMessage(CreativeWorlds.prefix.append(Component.text(exception.message ?: "Unknown error occurred.", NamedTextColor.RED)))
    }
}