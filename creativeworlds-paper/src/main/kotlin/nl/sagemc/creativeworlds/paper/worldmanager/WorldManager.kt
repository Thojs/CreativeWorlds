package nl.sagemc.creativeworlds.paper.worldmanager

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldUnloadEvent
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

object WorldManager : Listener {
    private val worlds: MutableList<CreativeWorld> = ArrayList()

    private val worldDirectory = File(Bukkit.getWorldContainer(), "/CreativeWorlds/")

    init {
        // Load all world instances
        worldDirectory.listFiles()?.filter { it.isDirectory }?.forEach {
            val player = Bukkit.getOfflinePlayer(UUID.fromString(it.name))
            loadInstances(player)
        }
    }

    /**
     * Function to get a CreativeWorld instance from a bukkit world.
     * @param world The bukkit world
     * @return A CreativeWorld instance if there is one.
     */
    fun getWorld(world: World) = worlds.find { it.bukkitWorld == world }

    /**
     * Creates a new world for the specified player.
     * @param player The owning player of the world.
     * @return A new CreativeWorld instance.
     */
    fun createWorld(player: OfflinePlayer): CreativeWorld {
        val world = CreativeWorld(player, getWorlds(player).lastOrNull()?.id?.plus(1) ?: 1)
        worlds.add(world)
        return world
    }

    /**
     * Deletes the specified CreativeWorld.
     * @param creativeWorld The world to delete.
     */
    fun deleteWorld(creativeWorld: CreativeWorld) {
        creativeWorld.unload()
        val worldDir = File(Bukkit.getWorldContainer().path + "/" + creativeWorld.bukkitWorld?.name)
        if (worldDir.exists()) worldDir.delete()
    }

    /**
     * Returns a world of a player at a specific index. Index will start at 1
     * @param player The player to query for.
     * @param index The index of the world.
     * @return A CreativeWorld instance if there is one.
     */
    fun getWorld(player: OfflinePlayer, index: Int): CreativeWorld? {
        val worlds = getWorlds(player)
        if (worlds.size >= index) return worlds[index]
        return null
    }

    /**
     * Function for listing all CreativeWorlds of a player.
     * @param player The player to query for.
     * @return A list of CreativeWorld instances.
     */
    fun getWorlds(player: OfflinePlayer): List<CreativeWorld> {
        loadInstances(player)
        return worlds.filter { it.owner == player }
    }

    @EventHandler
    fun onWorldUnload(e: WorldUnloadEvent) {
        val world = getWorld(e.world) ?: return
        world.unload()
    }

    fun unloadAllWorlds() {
        worlds.forEach { it.unload() }
    }

    private fun loadInstances(player: OfflinePlayer) {
        val directory = File(worldDirectory, "/${player.uniqueId}/")
        if (!directory.exists()) return
        File(worldDirectory, "/${player.uniqueId}/").listFiles()?.filter { it.isDirectory }?.forEach { file ->
            file.name.toIntOrNull()?.let { name ->
                if (worlds.find { it.owner == player && it.id == name } != null) return@forEach
                worlds.add(CreativeWorld(player, name))
            }
        }
    }

    val Player.worldLimit: Int
        get() {
            var worldLimit = 0
            this.effectivePermissions.filter { it.value || it.permission.startsWith("creativeworlds.worldlimit.")}.forEach {
                val limit = it.permission.split("creativeworlds.worldlimit.").getOrNull(1)?.toIntOrNull() ?: return@forEach
                if (limit > worldLimit) worldLimit = limit
            }
            return worldLimit
        }

    val Player.worldSize: Int
        get() {
            var worldSize = 0
            this.effectivePermissions.filter { it.value || it.permission.startsWith("creativeworlds.worldsize.")}.forEach {
                val limit = it.permission.split("creativeworlds.worldsize.").getOrNull(1)?.toIntOrNull() ?: return@forEach
                if (limit > worldSize) worldSize = limit
            }
            return worldSize
        }
}