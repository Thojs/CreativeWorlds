package nl.sagemc.creativeworlds.paper

import me.thojs.kommandhandler.bukkit.BukkitCommandHandler
import nl.sagemc.creativeworlds.paper.commands.WorldCommand
import nl.sagemc.creativeworlds.paper.utils.Utils
import nl.sagemc.creativeworlds.paper.utils.Utils.miniMessage
import nl.sagemc.creativeworlds.paper.worldmanager.EventListener
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags.*
import org.bukkit.plugin.java.JavaPlugin

class CreativeWorlds : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        instance = this

        Utils.registerEvents(this,
            WorldManager,
            EventListener,
            FarewellFlag,
            GreetingFlag,
            TitleFlag,
            NotifyEnterFlag,
            NotifyExitFlag,
            PVPFlag
        )

        BukkitCommandHandler(this) { _, _ ->

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