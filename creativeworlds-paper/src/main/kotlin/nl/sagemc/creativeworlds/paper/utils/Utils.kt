package nl.sagemc.creativeworlds.paper.utils

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.permissions.Permission
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

object Utils {
    /**
     * Initializes permissions.
     */
    fun permissions(vararg permissions: Permission) {
        permissions.forEach {
            if (!Bukkit.getPluginManager().permissions.contains(it)) return@forEach
            Bukkit.getPluginManager().addPermission(it)
        }
    }

    fun registerEvents(plugin: JavaPlugin, vararg listener: Listener) {
        listener.forEach { Bukkit.getPluginManager().registerEvents(it, plugin) }
    }

    /**
     * Function for creating a BukkitRunnable object.
     * @param run A function that will be used by the BukkitRunnable.
     * @return A new BukkitRunnable instance.
     */
    fun bukkitRunnable(run: () -> Unit) = object : BukkitRunnable() {
        override fun run() = run()
    }
}