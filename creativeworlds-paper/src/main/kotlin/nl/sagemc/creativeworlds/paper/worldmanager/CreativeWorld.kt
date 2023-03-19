package nl.sagemc.creativeworlds.paper.worldmanager

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.utils.Config
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager.worldSize
import nl.sagemc.creativeworlds.paper.worldmanager.flags.FlagContainer
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import java.util.*
import java.util.logging.Level

class CreativeWorld(val owner: OfflinePlayer, val id: Int) {
    private val worldName = "CreativeWorlds/${owner.uniqueId}/$id"
    private val config: Config = Config(File(Bukkit.getWorldContainer().path + "/" + worldName, "cw_properties.yml"))

    var unloadTimer: BukkitRunnable? = null

    var bukkitWorld: World? = null
        private set

    val trusted = OfflinePlayerContainer("trusted")
    val members = OfflinePlayerContainer("members")

    val denied = OfflinePlayerContainer("denied")

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
        config.getStringList("trusted").forEach { trusted += Bukkit.getOfflinePlayer(UUID.fromString(it)) }
        config.getStringList("members").forEach { members += Bukkit.getOfflinePlayer(UUID.fromString(it)) }
        config.getStringList("denied").forEach { denied += Bukkit.getOfflinePlayer(UUID.fromString(it)) }

        size = config.getInt("size", size)
        alias = config.getString("alias") ?: ""
        description = MiniMessage.miniMessage().deserialize(config.getString("description") ?: "")
    }

    fun updateConfig() {
        config["owner"] = owner.uniqueId.toString()

        trusted.update()
        members.update()
        denied.update()

        config.save()
    }

    fun load(): Boolean {
        if (bukkitWorld != null) return true

        CreativeWorlds.instance?.logger?.log(Level.INFO, "Starting CreativeWorld loading of ${owner.name}:$id")

        if (owner.isOnline && Bukkit.getPlayer(owner.uniqueId) != null) size = Bukkit.getPlayer(owner.uniqueId)?.worldSize ?: size

        val world = WorldCreator(worldName)
            .generateStructures(false)
            .type(WorldType.FLAT)
            .generator(CreativeWorldChunkGenerator(0))
            .createWorld() ?: return false

        // Set world properties
        world.apply {
            difficulty = Difficulty.PEACEFUL
            time = 8000

            setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false)
            setGameRule(GameRule.DO_MOB_SPAWNING, false)
            setGameRule(GameRule.DO_TRADER_SPAWNING, false)
            setGameRule(GameRule.DO_WEATHER_CYCLE, false)
            setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
            setGameRule(GameRule.DO_FIRE_TICK, false)
            setGameRule(GameRule.KEEP_INVENTORY, true)
            setGameRule(GameRule.RANDOM_TICK_SPEED, 0)
            setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
            setGameRule(GameRule.SHOW_DEATH_MESSAGES, false)

            // Create world border
            worldBorder.center = Location(world, 8.0, 0.0, 8.0)
            worldBorder.size = (size.toDouble() * 2 + 1) * 16
            worldBorder.warningDistance = -1
        }

        CreativeWorlds.instance?.logger?.log(Level.INFO, "Finished CreativeWorld loading of ${owner.name}:$id")

        // Set bukkitWorld variable and return true
        bukkitWorld = world
        return true
    }

    fun unload() {
        CreativeWorlds.instance?.logger?.log(Level.INFO, "Started unloading CreativeWorld of ${owner.name}:$id")
        updateConfig()
        bukkitWorld?.let {
            it.players.forEach { p -> p.teleport(Bukkit.getServer().worlds[0].spawnLocation) }
            Bukkit.unloadWorld(it, true)
        }
        bukkitWorld = null
        CreativeWorlds.instance?.logger?.log(Level.INFO, "Finished unloading CreativeWorld of ${owner.name}:$id")
    }

    fun getRights(p: OfflinePlayer): Rights {
        return if (p.isOp) {
            Rights.OP
        } else if (owner.uniqueId == p.uniqueId) {
            Rights.OWNER
        } else if (p in trusted) {
            Rights.TRUSTEE
        } else if (p in members) {
            Rights.MEMBER
        } else {
            Rights.VISITOR
        }
    }

    inner class OfflinePlayerContainer(private val key: String) {
        private val container = config.getStringList(key).map { UUID.fromString(it) }.toMutableSet()

        operator fun plusAssign(p: OfflinePlayer) {
            container.add(p.uniqueId)
        }

        operator fun minusAssign(p: OfflinePlayer) {
            container.remove(p.uniqueId)
        }

        operator fun remAssign(p: OfflinePlayer) {
            if (contains(p)) minusAssign(p) else plusAssign(p)
        }

        operator fun contains(p: OfflinePlayer): Boolean {
            return p.uniqueId in container
        }

        fun update() {
            config.set(key, container.map { it.toString() })
        }

        override fun toString(): String {
            return container.map { Bukkit.getOfflinePlayer(it).name }.joinToString(", ")
        }
    }
}