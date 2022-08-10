package nl.sagemc.creativeworlds.paper.worldmanager

import org.bukkit.World
import org.bukkit.entity.Player

class CreativeWorld(val world: World) {
    var owners: MutableList<Player> = ArrayList()
}