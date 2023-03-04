package nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags

import me.thojs.kommandhandler.core.parsers.LongParser
import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import nl.sagemc.creativeworlds.paper.worldmanager.flags.CommandFlag
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent

object TimeFlag : CommandFlag<Long>("time", 8000, LongParser id "time"), Listener {
    override fun serialize(obj: Long, section: ConfigurationSection) {
        section.set("value", obj)
    }

    override fun deserialize(obj: ConfigurationSection): Long? {
        if (!obj.contains("value")) return null
        return obj.getLong("value", defaultValue)
    }

    override fun onChange(world: CreativeWorld, newValue: Long) {
        world.bukkitWorld?.time = newValue
    }

    @EventHandler
    fun onWorldChange(e: PlayerChangedWorldEvent) {
        val world = WorldManager.getWorld(e.player.world) ?: return
        world.bukkitWorld?.time = world.flags[this]
    }
}