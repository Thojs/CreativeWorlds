package nl.sagemc.creativeworlds.paper.worldmanager

import nl.sagemc.creativeworlds.paper.utils.Config
import org.bukkit.*
import org.bukkit.entity.Player
import java.io.File
import java.util.*

class CreativeWorld(val owner: UUID, val id: Int) {
    private val worldName = "${owner}_$id"
    private val config: Config = Config(File(Bukkit.getWorldContainer().path + "/" + worldName, "cw_properties.yml"))

    var bukkitWorld: World? = null
        private set

    val loaded: Boolean
        get() = bukkitWorld != null

    val trusted: MutableList<Player> = ArrayList()
    val members: MutableList<Player> = ArrayList()

    val banned: MutableList<Player> = ArrayList()

    var size: Int = 10

    var alias: String = ""

    init {
        config.getStringList("trusted").map { Bukkit.getPlayer(it) }.forEach { it?.let { trusted.add(it) } }
        config.getStringList("members").map { Bukkit.getPlayer(it) }.forEach { it?.let { members.add(it) } }
        config.getStringList("banned").map { Bukkit.getPlayer(it) }.forEach { it?.let { banned.add(it) } }
        alias = config.getString("alias") ?: ""
        size = config.getInt("size")
    }

    fun updateConfig() {
        config["owner"] = owner.toString()

        config["trusted"] = trusted.map { it.uniqueId.toString() }
        config["members"] = members.map { it.uniqueId.toString() }
        config["banned"] = banned.map { it.uniqueId.toString() }

        config["alias"] = alias
        config.save()
    }

    fun load(): World {
        if (bukkitWorld != null) return bukkitWorld!!

        bukkitWorld = WorldCreator(worldName)
            .generateStructures(false)
            .type(WorldType.FLAT)
            .generator(CreativeWorldChunkGenerator(0, size))
            .createWorld()

        bukkitWorld?.difficulty = Difficulty.PEACEFUL
        bukkitWorld?.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false)

        return bukkitWorld!!
    }

    fun unload() {
        bukkitWorld?.let {
            Bukkit.unloadWorld(it, true)
            bukkitWorld = null
        }
    }
}