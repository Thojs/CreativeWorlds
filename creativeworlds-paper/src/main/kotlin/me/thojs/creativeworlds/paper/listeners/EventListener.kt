package me.thojs.creativeworlds.paper.listeners

import me.thojs.creativeworlds.paper.CreativeWorlds
import me.thojs.creativeworlds.paper.utils.Utils
import me.thojs.creativeworlds.paper.worldmanager.Rights
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockFadeEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.event.vehicle.VehicleDamageEvent
import java.util.logging.Level

object EventListener : Listener {
    // Prevent coral blocks from dying.
    @EventHandler
    fun onCoralChange(e: BlockFadeEvent) {
        if (e.block.type.name.contains("CORAL")) e.isCancelled = true
    }

    @EventHandler
    fun onEntityInteract(e: PlayerInteractEntityEvent) {
        e.cancelEvent(e.player)
    }

    @EventHandler
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

    // World unload stuff
    @EventHandler
    fun onWorldMove(e: PlayerChangedWorldEvent) {
        scheduleUnload(e.from)

        val creativeWorld = WorldManager.getWorld(e.player.world) ?: return
        if (creativeWorld.unloadTimer?.isCancelled == false) {
            creativeWorld.unloadTimer?.cancel()
            creativeWorld.unloadTimer = null
        }
    }

    @EventHandler
    fun onLeave(e: PlayerQuitEvent) {
        scheduleUnload(e.player.world, e.player)
    }

    // Prevent users from leaving the world border.
    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        e.isCancelled = !e.to.world.worldBorder.isInside(e.to)
    }

    @EventHandler
    fun onTeleport(e: PlayerTeleportEvent) {
        e.isCancelled = !e.to.world.worldBorder.isInside(e.to)
    }

    private const val worldUnloadMinutes = 15

    private fun scheduleUnload(world: World, player: Player? = null) {
        val creativeWorld = WorldManager.getWorld(world) ?: return
        if (world.players.apply { remove(player) }.isEmpty()) {
            if (creativeWorld.unloadTimer?.isCancelled == false) creativeWorld.unloadTimer?.cancel()
            creativeWorld.unloadTimer = Utils.bukkitRunnable {
                CreativeWorlds.instance?.logger?.log(
                    Level.INFO,
                    "No activity detected in world ${creativeWorld.owner.name}:${creativeWorld.id} for $worldUnloadMinutes minutes, unloading it."
                )
                creativeWorld.unload()
            }
            CreativeWorlds.instance?.let { creativeWorld.unloadTimer?.runTaskLater(it, (20*60*worldUnloadMinutes).toLong()) }
        }
    }

    private fun allow(p: Player): Boolean {
        val world = WorldManager.getWorld(p.world) ?: return true
        return world.getRights(p) >= Rights.MEMBER
    }

    private fun Cancellable.cancelEvent(p: Player) {
        if (!allow(p)) this.isCancelled = true
    }
}