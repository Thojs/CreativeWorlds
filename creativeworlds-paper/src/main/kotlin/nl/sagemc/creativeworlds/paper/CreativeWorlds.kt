package nl.sagemc.creativeworlds.paper

import me.thojs.kommandhandler.bukkit.BukkitCommandHandler
import me.thojs.kommandhandler.core.exceptions.ArgumentParseException
import me.thojs.kommandhandler.core.exceptions.NoExecutorException
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.commands.WorldCommand
import nl.sagemc.creativeworlds.paper.utils.Utils
import nl.sagemc.creativeworlds.paper.utils.Utils.miniMessage
import nl.sagemc.creativeworlds.paper.worldmanager.EventListener
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import nl.sagemc.creativeworlds.paper.worldmanager.adapters.WorldEditAdapter
import nl.sagemc.creativeworlds.paper.worldmanager.flags.FlagContainer
import nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags.*
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class CreativeWorlds : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        instance = this

        val hasWorldEdit = server.pluginManager.getPlugin("WorldEdit") != null
        if (hasWorldEdit) {
            WorldEditAdapter
            logger.log(Level.INFO, "Found WorldEdit plugin installed, using WorldEdit hook.")
        }

        Utils.registerEvents(this,
            WorldManager,
            EventListener,
            FarewellFlag,
            GreetingFlag,
            TitleFlag,
            NotifyEnterFlag,
            NotifyExitFlag,
            PVPFlag,
            GuestGamemodeFlag
        )

        FlagContainer.globalFlags.addAll(listOf(
            FarewellFlag,
            GreetingFlag,
            NotifyEnterFlag,
            NotifyExitFlag,
            PVPFlag,
            GuestGamemodeFlag
        ))

        BukkitCommandHandler(this) { sender, exception ->
            sender.sendMessage(prefix.append(
                when (exception) {
                    is NoExecutorException -> Component.text("Could not find an executor with the provided arguments.")
                    is ArgumentParseException -> Component.text("Could not parse argument ${exception.index+1}")
                    else -> Component.text("Unknown error occurred whilst executing the command.")
                }.color(NamedTextColor.RED)
            ))
        }.registerCommands(
            WorldCommand
        )
    }

    override fun onDisable() {
        // Plugin shutdown logic

        WorldManager.unloadAllWorlds()

        instance = null
    }

    companion object {
        var instance: CreativeWorlds? = null

        val prefix = miniMessage("<gold>Sage<yellow>Creative</yellow>Worlds</gold> <dark_gray>|></dark_gray> ")
    }
}