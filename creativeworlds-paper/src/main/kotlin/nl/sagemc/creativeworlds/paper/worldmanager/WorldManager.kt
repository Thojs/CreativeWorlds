package nl.sagemc.creativeworlds.paper.worldmanager

import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import org.bukkit.entity.Player
import java.io.File

// TODO: fix dit
object WorldManager {
    val worlds: HashMap<Player, MutableList<CreativeWorld>> = HashMap()

    fun loadWorld(player: Player, id: Int): CreativeWorld? {
        val worldName = "${player.uniqueId}_$id"
        val world = File(Bukkit.getWorldContainer().path + "/" + worldName)
        if (!world.exists()) return null

        val bukkitWorld = WorldCreator(worldName).createWorld()

        return bukkitWorld?.let { CreativeWorld(it) }
    }

    fun createWorld(player: Player) {

    }

    fun getWorlds(player: Player): List<CreativeWorld> {
        Bukkit.getWorldContainer().listFiles()?.filter {
            it.name.startsWith(player.uniqueId.toString() + "_")
        }?.filter {
            File(it.path.toString(), "level.dat").exists()
        }
        return emptyList()
    }
}