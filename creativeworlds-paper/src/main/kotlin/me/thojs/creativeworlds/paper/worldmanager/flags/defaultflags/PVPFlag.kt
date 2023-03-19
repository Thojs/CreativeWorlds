package me.thojs.creativeworlds.paper.worldmanager.flags.defaultflags

import me.thojs.kommandhandler.core.parsers.BooleanParser
import me.thojs.creativeworlds.paper.worldmanager.CreativeWorld
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import me.thojs.creativeworlds.paper.worldmanager.flags.CommandFlag
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

object PVPFlag: CommandFlag<Boolean>("pvp", false, BooleanParser id "allow"), Listener {
    override fun serialize(obj: Boolean, section: ConfigurationSection) {
        section["value"] = obj
    }

    override fun deserialize(obj: ConfigurationSection): Boolean {
        return obj.getBoolean("value")
    }

    override fun onChange(world: CreativeWorld, newValue: Boolean) {}

    @EventHandler
    fun onDamage(e: EntityDamageByEntityEvent) {
        if (e.damager !is Player) return
        val world = WorldManager.getWorld(e.damager.world) ?: return
        if (!world.flags[this]) return
        e.isCancelled = true
    }
}