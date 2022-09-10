package nl.sagemc.creativeworlds.paper

import nl.sagemc.creativeworlds.paper.commands.WorldCommand
import nl.sagemc.creativeworlds.paper.utils.Utils
import nl.sagemc.creativeworlds.paper.utils.commandhandler.BukkitCommandHandler
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.plugin.java.JavaPlugin

class CreativeWorlds : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        instance = this

        Utils.registerEvents(this,
            WorldManager
        )

        BukkitCommandHandler(this).registerCommands(
            WorldCommand
        )
    }

    override fun onDisable() {
        // Plugin shutdown logic
        instance = null
    }

    companion object {
        var instance: CreativeWorlds? = null
    }
}