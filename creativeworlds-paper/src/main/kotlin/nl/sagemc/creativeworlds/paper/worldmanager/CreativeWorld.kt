package nl.sagemc.creativeworlds.paper.worldmanager

import nl.sagemc.creativeworlds.paper.utils.Config
import org.bukkit.*
import java.io.File
import java.util.*

class CreativeWorld(val owner: UUID, val id: Int) {
    private val worldName = "CreativeWorlds/${owner}/$id"
    private val config: Config = Config(File(Bukkit.getWorldContainer().path + "/" + worldName, "cw_properties.yml"))

    var bukkitWorld: World? = null
        private set

    val trusted: MutableList<OfflinePlayer> = ArrayList()
    val members: MutableList<OfflinePlayer> = ArrayList()

    val denied: MutableList<OfflinePlayer> = ArrayList()

    var size: Int = 15

    var alias: String = ""

    init {
        config.getStringList("trusted").map { Bukkit.getOfflinePlayer(it) }.forEach { trusted.add(it) }
        config.getStringList("members").map { Bukkit.getOfflinePlayer(it) }.forEach { members.add(it) }
        config.getStringList("denied").map { Bukkit.getOfflinePlayer(it) }.forEach { denied.add(it) }

        size = config.getInt("size", size)
        alias = config.getString("alias") ?: ""
    }

    fun updateConfig() {
        config["owner"] = owner.toString()

        config["trusted"] = trusted.map { it.uniqueId.toString() }
        config["members"] = members.map { it.uniqueId.toString() }
        config["denied"] = denied.map { it.uniqueId.toString() }

        config["alias"] = alias
        config.save()
    }

    fun load(): Boolean {
        if (bukkitWorld != null) return true

        val world = WorldCreator(worldName)
            .generateStructures(false)
            .type(WorldType.FLAT)
            .generator(CreativeWorldChunkGenerator(0))
            .createWorld() ?: return false

        // Set world properties
        world.apply {
            difficulty = Difficulty.PEACEFUL

            setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false)
            setGameRule(GameRule.DO_MOB_SPAWNING, false)
            setGameRule(GameRule.DO_TRADER_SPAWNING, false)
            setGameRule(GameRule.DO_WEATHER_CYCLE, false)

            // Create world border
            worldBorder.center = Location(world, 8.0, 0.0, 8.0)
            worldBorder.size = (size.toDouble() * 2 + 1) * 16
            worldBorder.warningDistance = -1
        }

        // Set bukkitWorld variable and return true
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