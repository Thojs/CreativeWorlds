package nl.sagemc.creativeworlds.paper.worldmanager

import org.bukkit.World
import org.bukkit.entity.Player

// TODO: fix dit
object WorldManager {
    val worlds: HashMap<Player, MutableList<CreativeWorld>> = HashMap()

    fun getWorld(world: World): CreativeWorld? {
        return null
    }

    fun createWorld(player: Player) {

    }

    fun getWorlds(player: Player): List<CreativeWorld>? {
        return worlds[player]?.toList()
    }
}