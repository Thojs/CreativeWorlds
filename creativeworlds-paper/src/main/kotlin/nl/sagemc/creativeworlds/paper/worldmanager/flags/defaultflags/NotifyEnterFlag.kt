package nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags

import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import nl.sagemc.creativeworlds.paper.worldmanager.flags.Flag
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

object NotifyEnterFlag : Flag<Boolean>("notify-enter", false), Listener {
    override fun serialize(obj: Boolean) = obj.toString()

    override fun deserialize(obj: String): Boolean? {
        return obj.toBooleanStrictOrNull()
    }

    override fun onChange(world: CreativeWorld, newValue: Boolean) {}


    @EventHandler
    fun onWorldEnter(e: PlayerTeleportEvent) {
        if (e.from.world == e.to.world) return
        if (e.player.isOp) return

        val world = WorldManager.getWorld(e.to.world) ?: return

        if (world.flags[this] && world.owner.isOnline) {
            (world.owner as Player).sendMessage("Player ${e.player.name} entered your world.")
        }
    }
}