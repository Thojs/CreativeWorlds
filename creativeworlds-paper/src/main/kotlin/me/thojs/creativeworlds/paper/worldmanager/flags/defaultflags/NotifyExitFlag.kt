package me.thojs.creativeworlds.paper.worldmanager.flags.defaultflags

import me.thojs.kommandhandler.core.parsers.BooleanParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import me.thojs.creativeworlds.paper.CreativeWorlds
import me.thojs.creativeworlds.paper.worldmanager.CreativeWorld
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import me.thojs.creativeworlds.paper.worldmanager.flags.CommandFlag
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

object NotifyExitFlag : CommandFlag<Boolean>("notify-exit", false, BooleanParser id "value"), Listener {
    override fun serialize(obj: Boolean, section: ConfigurationSection) {
        section["value"] = obj
    }

    override fun deserialize(obj: ConfigurationSection): Boolean {
        return obj.getBoolean("value")
    }

    override fun onChange(world: CreativeWorld, newValue: Boolean) {}

    @EventHandler
    fun onWorldLeave(e: PlayerTeleportEvent) {
        if (e.from.world == e.to.world) return

        val world = WorldManager.getWorld(e.from.world) ?: return

        if (e.player.isOp || world.owner.uniqueId == e.player.uniqueId) return

        if (world.flags[this] &&  world.owner is Player) {
            world.owner.sendMessage(CreativeWorlds.prefix.append(Component.text("Player ${e.player.name} left your world.").color(NamedTextColor.GREEN)))
        }
    }
}