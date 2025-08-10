package me.thojs.creativeworlds.paper.commands

import me.thojs.creativeworlds.paper.CreativeWorlds
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.incendo.cloud.kotlin.extension.buildAndRegister
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.paper.util.sender.Source

object CreativeWorldsCommand {
    fun register(manager: PaperCommandManager<Source>) {
        manager.buildAndRegister("creativeworlds") {
            handler {
                it.sender().source().sendMessage(CreativeWorlds.prefix.append(Component.text("This server is running CreativeWorlds version ${CreativeWorlds.instance?.pluginMeta?.version ?: "unknown"}").color(NamedTextColor.GREEN)))
            }
        }
    }
}