package nl.sagemc.creativeworlds.paper.worldmanager

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

object EventListener : Listener {
    @EventHandler
    fun onBreak(e: BlockBreakEvent) {
        if (!allow(e.player)) e.isCancelled = true
    }

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        if (!allow(e.player)) e.isCancelled = true
    }

    @EventHandler
    fun onInteractEntity(e: PlayerInteractAtEntityEvent) {
        if (!allow(e.player)) e.isCancelled = true
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        if (!allow(e.player)) e.isCancelled = true
    }

    @EventHandler
    fun onDrop(e: PlayerDropItemEvent) {
        if (!allow(e.player)) e.isCancelled = true
    }

    @EventHandler
    fun onPickup(e: EntityPickupItemEvent) {
        if (e.entity !is Player) return
        if (!allow(e.entity as Player)) e.isCancelled = true
    }

    private fun allow(p: Player): Boolean {
        val world = WorldManager.getWorld(p.world) ?: return true
        return world.owner == p || world.members.contains(p) && world.owner.isOnline || world.trusted.contains(p)
    }
}