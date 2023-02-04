package nl.sagemc.creativeworlds.paper.worldmanager

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import nl.sagemc.creativeworlds.paper.utils.Config
import nl.sagemc.creativeworlds.paper.worldmanager.flags.FlagContainer
import org.bukkit.*
import org.bukkit.entity.Player
import java.io.File
import java.util.*

class CreativeWorld(val owner: OfflinePlayer, val id: Int) {
    private val worldName = "CreativeWorlds/${owner.uniqueId}/$id"
    private val config: Config = Config(File(Bukkit.getWorldContainer().path + "/" + worldName, "cw_properties.yml"))

    var bukkitWorld: World? = null
        private set

    val trusted = mutableListOf<OfflinePlayer>()
    val members = mutableListOf<OfflinePlayer>()

    val denied = mutableListOf<OfflinePlayer>()

    var size: Int = 15
        set(value) {
            config["size"] = value
            updateConfig()
            field = value
        }

    var alias: String? = null
        set(value) {
            config["alias"] = value
            updateConfig()
            field = value
        }

    var description: Component = Component.empty()
        set(value) {
            config["description"] = MiniMessage.miniMessage().serialize(value)
            updateConfig()
            field = value
        }

    val flags = FlagContainer(this, config.getConfigurationSection("flags") ?: config.createSection("flags"), this::updateConfig)

    init {
        config.getStringList("trusted").map { Bukkit.getOfflinePlayer(UUID.fromString(it)) }.forEach { trusted.add(it) }
        config.getStringList("members").map { Bukkit.getOfflinePlayer(UUID.fromString(it)) }.forEach { members.add(it) }
        config.getStringList("denied").map { Bukkit.getOfflinePlayer(UUID.fromString(it)) }.forEach { denied.add(it) }

        size = config.getInt("size", size)
        alias = config.getString("alias") ?: ""
        description = MiniMessage.miniMessage().deserialize(config.getString("description") ?: "")
    }

    fun updateConfig() {
        config["owner"] = owner.uniqueId.toString()

        config["trusted"] = trusted.map { it.uniqueId.toString() }
        config["members"] = members.map { it.uniqueId.toString() }
        config["denied"] = denied.map { it.uniqueId.toString() }
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
            setGameRule(GameRule.DO_FIRE_TICK, false)
            setGameRule(GameRule.KEEP_INVENTORY, true)
            setGameRule(GameRule.RANDOM_TICK_SPEED, 0)

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
        updateConfig()
        bukkitWorld?.let {
            Bukkit.unloadWorld(it, true)
            bukkitWorld = null
        }
    }

    fun hasRights(p: Player): Boolean {
        return owner.uniqueId == p.uniqueId || members.contains(p) && owner.isOnline || trusted.contains(p) || p.isOp
    }
}