package nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import nl.sagemc.creativeworlds.paper.commands.ComponentParser
import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import nl.sagemc.creativeworlds.paper.worldmanager.flags.CommandFlag
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

object GreetingFlag : CommandFlag<Component>("greeting", Component.empty(), ComponentParser id "greetingMessage"), Listener {
    override fun serialize(obj: Component, section: ConfigurationSection) {
        section["message"] = MiniMessage.miniMessage().serialize(obj)
    }

    override fun deserialize(obj: ConfigurationSection): Component {
        return MiniMessage.miniMessage().deserialize(obj.getString("message") ?: "")
    }

    override fun onChange(world: CreativeWorld, newValue: Component) {}

    @EventHandler
    fun onWorldEnter(e: PlayerTeleportEvent) {
        if (e.from.world == e.to.world) return

        val world = WorldManager.getWorld(e.to.world) ?: return

        val value = world.flags[this]
        if (value == Component.empty()) return
        e.player.sendMessage(value)
    }
}