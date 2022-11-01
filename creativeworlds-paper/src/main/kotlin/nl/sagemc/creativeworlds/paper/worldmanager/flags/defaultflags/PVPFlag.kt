package nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags

import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import nl.sagemc.creativeworlds.paper.worldmanager.flags.Flag
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

object PVPFlag: Flag<Boolean>("pvp", false), Listener {
    override fun serialize(obj: Boolean) = obj.toString()

    override fun deserialize(obj: String): Boolean? {
        return obj.toBooleanStrictOrNull()
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