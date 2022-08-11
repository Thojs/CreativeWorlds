package nl.sagemc.creativeworlds.paper.worldmanager

import nl.sagemc.creativeworlds.paper.utils.Config
import nl.sagemc.creativeworlds.paper.worldmanager.flags.Flag
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.entity.Player
import java.io.File

class CreativeWorld(val owner: Player, val id: Int) {
    private val worldName = "${owner.uniqueId}_$id"
    private val config: Config = Config(File(Bukkit.getWorldContainer().path + "/" + worldName, "cw_properties.yml"))

    var bukkitWorld: World? = null
        private set

    var loaded: Boolean = false
        private set

    val trusted: MutableList<Player> = ArrayList()
    val members: MutableList<Player> = ArrayList()

    val banned: MutableList<Player> = ArrayList()

    var alias: String = ""
    val flags: MutableList<Flag<*>> = ArrayList()

    init {
        config.getStringList("trusted").map { Bukkit.getPlayer(it) }.forEach { it?.let { trusted.add(it) } }
        config.getStringList("members").map { Bukkit.getPlayer(it) }.forEach { it?.let { members.add(it) } }
        config.getStringList("banned").map { Bukkit.getPlayer(it) }.forEach { it?.let { banned.add(it) } }
    }

    fun updateConfig() {
        config["owner"] = owner.uniqueId

        config["trusted"] = trusted.map { it.uniqueId.toString() }
        config["members"] = members.map { it.uniqueId.toString() }
        config["banned"] = banned.map { it.uniqueId.toString() }

        config["alias"] = alias
        config.save()
    }

    fun load() {
        if (loaded) return
        bukkitWorld = WorldCreator(worldName).createWorld()
    }

    fun unload() {
        bukkitWorld?.let {
            Bukkit.unloadWorld(it, true)
            loaded = false
        }
    }
}