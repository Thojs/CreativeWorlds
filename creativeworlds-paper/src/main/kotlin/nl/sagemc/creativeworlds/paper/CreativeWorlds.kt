package nl.sagemc.creativeworlds.paper

import nl.sagemc.creativeworlds.paper.commands.WorldCommand
import nl.sagemc.creativeworlds.paper.utils.commandhandler.BukkitCommandHandler
import org.bukkit.plugin.java.JavaPlugin

class CreativeWorlds : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        BukkitCommandHandler(this).registerCommands(
            WorldCommand
        )
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}