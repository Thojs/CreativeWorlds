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

    var size: Int = 25

    var alias: String = ""

    init {
        config.getStringList("trusted").map { Bukkit.getPlayer(it) }.forEach { it?.let { trusted.add(it) } }
        config.getStringList("members").map { Bukkit.getPlayer(it) }.forEach { it?.let { members.add(it) } }
        config.getStringList("banned").map { Bukkit.getPlayer(it) }.forEach { it?.let { banned.add(it) } }
        alias = config.getString("alias") ?: ""
        size = config.getInt("size", size)
    }

    fun updateConfig() {
        config["owner"] = owner.toString()

        config["trusted"] = trusted.map { it.uniqueId.toString() }
        config["members"] = members.map { it.uniqueId.toString() }
        config["banned"] = banned.map { it.uniqueId.toString() }

        config["alias"] = alias
        config.save()
    }

    fun load(): Boolean {
        if (bukkitWorld != null) return true

        val world = WorldCreator(worldName)
            .generateStructures(false)
            .type(WorldType.FLAT)
            .generator(CreativeWorldChunkGenerator(0, size))
            .createWorld() ?: return false

        // Set world properties
        world.difficulty = Difficulty.PEACEFUL
        world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false)
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        world.setGameRule(GameRule.DO_TRADER_SPAWNING, false)

        // Create world border
        val wbSize = ((size+1)*16/2).toDouble()

        val border = world.worldBorder
        border.center = Location(world, wbSize, 0.0, wbSize)
        border.size = wbSize*2
        border.warningDistance = -1

        bukkitWorld = world

        return true
    }

    fun unload() {
        bukkitWorld?.let {
            Bukkit.unloadWorld(it, true)
            bukkitWorld = null
        }
    }
}