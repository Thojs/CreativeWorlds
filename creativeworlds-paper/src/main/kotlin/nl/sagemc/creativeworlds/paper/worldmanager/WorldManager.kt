package nl.sagemc.creativeworlds.paper.worldmanager

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.world.ChunkLoadEvent
import java.io.File
import java.util.UUID

// TODO: fix dit
object WorldManager : Listener {
    private val worlds: MutableList<CreativeWorld> = ArrayList()

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
    fun createWorld(player: UUID) {
        worlds.add(CreativeWorld(player, getWorlds(player).lastOrNull()?.id?.plus(1) ?: 1))
    }

    /**
     * Deletes the specified CreativeWorld.
     * @param creativeWorld The world to delete.
     */
    fun deleteWorld(creativeWorld: CreativeWorld) {
        creativeWorld.unload()
        creativeWorld.owner
        val worldDir = File(Bukkit.getWorldContainer().path + "/" + creativeWorld.bukkitWorld?.name)
        if (worldDir.exists()) worldDir.delete()
    }

    /**
     * Returns a world of a player at a specific index. Index will start at 1
     * @param player The player to query for.
     * @param index The index of the world.
     * @return A CreativeWorld instance if there is one.
     */
    fun getWorld(player: UUID, index: Int): CreativeWorld? {
        val worlds = getWorlds(player)
        if (worlds.size >= index) return worlds[index]
        return null
    }

    /**
     * Function for listing all CreativeWorlds of a player.
     * @param player The player to query for.
     * @return A list of CreativeWorld instances.
     */
    fun getWorlds(player: UUID): List<CreativeWorld> {
        return worlds.filter { it.owner == player }
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player

        // Load CreativeWorld instances
        Bukkit.getWorldContainer().listFiles()?.filter { it.isDirectory && it.name.startsWith("${player.uniqueId}_") }?.forEach { file ->
            file.nameWithoutExtension.split("_").find { it.toIntOrNull() != null }?.toInt()?.let {
                worlds.add(CreativeWorld(player.uniqueId, it))
            }
        }
    }

    // TODO: Maybe unload?
    @EventHandler
    fun onLeave(e: PlayerQuitEvent) {
        val player = e.player

    }

    @EventHandler
    fun onChunkLoad(e: ChunkLoadEvent) {
        if (e.chunk.x >= 10) {

        }
    }
}