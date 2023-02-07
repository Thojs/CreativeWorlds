package nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags

import me.thojs.kommandhandler.core.parsers.BooleanParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import nl.sagemc.creativeworlds.paper.worldmanager.flags.CommandFlag
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

object NotifyEnterFlag : CommandFlag<Boolean>("notify-enter", false, BooleanParser id "value"), Listener {
    override fun serialize(obj: Boolean, section: ConfigurationSection) {
        section["value"] = obj
    }

    override fun deserialize(obj: ConfigurationSection): Boolean {
        return obj.getBoolean("value")
    }

    override fun onChange(world: CreativeWorld, newValue: Boolean) {}


    @EventHandler
    fun onWorldEnter(e: PlayerTeleportEvent) {
        if (e.from.world == e.to.world) return

        val world = WorldManager.getWorld(e.to.world) ?: return

        if (e.player.isOp && !world.owner.isOp) return

        if (world.flags[this] && world.owner.isOnline) {
            (world.owner as Player).sendMessage(CreativeWorlds.prefix.append(Component.text("Player ${e.player.name} entered your world.").color(NamedTextColor.GREEN)))
        }
    }
}