package nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.TitlePart
import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import nl.sagemc.creativeworlds.paper.worldmanager.flags.Flag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

object TitleFlag: Flag<Component>("title", Component.empty()), Listener {
    override fun serialize(obj: Component): String {
        return MiniMessage.miniMessage().serialize(obj)
    }

    override fun deserialize(obj: String): Component {
        return MiniMessage.miniMessage().deserialize(obj)
    }

    override fun onChange(world: CreativeWorld, newValue: Component) {}

    @EventHandler
    fun onWorldEnter(e: PlayerTeleportEvent) {
        if (e.from.world == e.to.world) return

        val world = WorldManager.getWorld(e.to.world) ?: return

        val value = world.flags[this]
        if (value == Component.empty()) return
        e.player.sendTitlePart(TitlePart.TITLE, value)
    }
}