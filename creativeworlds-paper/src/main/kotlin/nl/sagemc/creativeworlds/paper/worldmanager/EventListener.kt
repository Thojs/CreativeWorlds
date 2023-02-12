package nl.sagemc.creativeworlds.paper.worldmanager

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.vehicle.VehicleDamageEvent

object EventListener : Listener {
    @EventHandler(priority = EventPriority.LOW)
    fun onBlockBreak(e: BlockBreakEvent) {
        e.cancelEvent(e.player)
    }

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        e.cancelEvent(e.player)
    }

    @EventHandler
    fun onInteractEntity(e: PlayerInteractAtEntityEvent) {
        e.cancelEvent(e.player)
    }

    @EventHandler
    fun onEntityDamage(e: EntityDamageByEntityEvent) {
        e.cancelEvent(e.damager as? Player ?: return)
    }

    @EventHandler
    fun onVehicleDamage(e: VehicleDamageEvent) {
        e.cancelEvent(e.attacker as? Player ?: return)
    }

    @EventHandler
    fun onHangingEntityBreak(e: HangingBreakByEntityEvent) {
        e.cancelEvent(e.remover as? Player ?: return)
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        e.cancelEvent(e.player)
    }

    @EventHandler
    fun onDrop(e: PlayerDropItemEvent) {
        e.cancelEvent(e.player)
    }

    @EventHandler
    fun onPickup(e: EntityPickupItemEvent) {
        e.cancelEvent(e.entity as? Player ?: return)
    }

    @EventHandler
    fun onExplosion(e: EntityExplodeEvent) {
        e.blockList().clear()
    }

    private fun allow(p: Player): Boolean {
        val world = WorldManager.getWorld(p.world) ?: return true
        return world.getRights(p) >= Rights.MEMBER
    }

    private fun Cancellable.cancelEvent(p: Player) {
        if (!allow(p)) this.isCancelled = true
    }
}