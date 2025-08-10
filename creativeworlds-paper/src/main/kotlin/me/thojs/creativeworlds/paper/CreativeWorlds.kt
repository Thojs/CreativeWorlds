package me.thojs.creativeworlds.paper

import me.thojs.creativeworlds.paper.utils.Utils
import me.thojs.creativeworlds.paper.utils.Utils.miniMessage
import me.thojs.creativeworlds.paper.listeners.EventListener
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import me.thojs.creativeworlds.paper.adapters.WorldEditAdapter
import me.thojs.creativeworlds.paper.commands.CreativeWorldsCommand
import me.thojs.creativeworlds.paper.commands.WorldCommand
import me.thojs.creativeworlds.paper.commands.exceptions.CreativeWorldsExceptionHandler
import me.thojs.creativeworlds.paper.worldmanager.flags.FlagContainer
import me.thojs.creativeworlds.paper.worldmanager.flags.defaultflags.*
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.exception.CommandExecutionException
import org.incendo.cloud.exception.handling.ExceptionHandler
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.kotlin.extension.registerHandler
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper
import org.incendo.cloud.paper.util.sender.Source
import java.util.logging.Level

class CreativeWorlds : JavaPlugin() {
    lateinit var commandManager: PaperCommandManager<Source>
        private set

    override fun onEnable() {
        // Plugin startup logic
        instance = this

        // Register commands
        commandManager = PaperCommandManager.builder(PaperSimpleSenderMapper.simpleSenderMapper())
            .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
            .buildOnEnable(this)

        commandManager.exceptionController().registerHandler(CommandExecutionException::class.java, ExceptionHandler.unwrappingHandler())
        commandManager.exceptionController().registerHandler(CreativeWorldsExceptionHandler)

        WorldCommand.register(commandManager)
        CreativeWorldsCommand.register(commandManager)

        // Adapters
        val hasWorldEdit = server.pluginManager.getPlugin("WorldEdit") != null
        if (hasWorldEdit) {
            WorldEditAdapter.register()
            logger.log(Level.INFO, "Found WorldEdit plugin installed, using WorldEdit hook.")
        }

        // Other stuff
        Utils.registerEvents(this,
            WorldManager,
            EventListener,
            FarewellFlag,
            GreetingFlag,
            TitleFlag,
            NotifyEnterFlag,
            NotifyExitFlag,
            PVPFlag,
            GuestGamemodeFlag,
            TimeFlag
        )

        FlagContainer.globalFlags.addAll(listOf(
            FarewellFlag,
            GreetingFlag,
            NotifyEnterFlag,
            NotifyExitFlag,
            PVPFlag,
            GuestGamemodeFlag,
            TimeFlag
        ))
    }

    override fun onDisable() {
        // Plugin shutdown logic
        WorldManager.unloadAllWorlds()

        instance = null
    }

    companion object {
        var instance: CreativeWorlds? = null

        val prefix = miniMessage("<gold>Creative<yellow>Worlds</yellow> <dark_gray>|></dark_gray> ")
    }
}