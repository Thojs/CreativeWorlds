package nl.sagemc.creativeworlds.paper.worldmanager

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
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
    @EventHandler
    fun onBreak(e: BlockBreakEvent) {
        e.isCancelled = !allow(e.player)
    }

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        e.isCancelled = !allow(e.player)
    }

    @EventHandler
    fun onInteractEntity(e: PlayerInteractAtEntityEvent) {
        e.isCancelled = !allow(e.player)
    }

    @EventHandler
    fun onEntityDamage(e: EntityDamageByEntityEvent) {
        e.isCancelled = !allow(e.damager as? Player ?: return)
    }

    @EventHandler
    fun onVehicleDamage(e: VehicleDamageEvent) {
        e.isCancelled = !allow(e.attacker as? Player ?: return)
    }

    @EventHandler
    fun onHangingEntityBreak(e: HangingBreakByEntityEvent) {
        e.isCancelled = !allow(e.remover as? Player ?: return)
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        e.isCancelled = !allow(e.player)
    }

    @EventHandler
    fun onDrop(e: PlayerDropItemEvent) {
        e.isCancelled = !allow(e.player)
    }

    @EventHandler
    fun onPickup(e: EntityPickupItemEvent) {
        e.isCancelled = !allow(e.entity as? Player ?: return)
    }

    @EventHandler
    fun onExplosion(e: EntityExplodeEvent) {
        e.blockList().clear()
    }

    private fun allow(p: Player): Boolean {
        val world = WorldManager.getWorld(p.world) ?: return true
        return world.owner.uniqueId == p.uniqueId || world.members.contains(p) && world.owner.isOnline || world.trusted.contains(p) || p.isOp
    }
}