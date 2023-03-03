package nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags

import me.thojs.kommandhandler.core.parsers.EnumParser
import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import nl.sagemc.creativeworlds.paper.worldmanager.Rights
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import nl.sagemc.creativeworlds.paper.worldmanager.flags.CommandFlag
import org.bukkit.GameMode
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent

object GuestGamemodeFlag : CommandFlag<GameMode>("guest-gamemode", GameMode.CREATIVE, EnumParser(GameMode::class.java) id "gamemode"), Listener {
    override fun serialize(obj: GameMode, section: ConfigurationSection) {
        section.set("value", obj.toString())
    }

    override fun deserialize(obj: ConfigurationSection): GameMode? {
        val str = obj.getString("value") ?: return null
        return try {
            GameMode.valueOf(str)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    override fun onChange(world: CreativeWorld, newValue: GameMode) {
        world.bukkitWorld?.players?.forEach {
            if (world.getRights(it) < Rights.MEMBER) it.gameMode = newValue
        }
    }

    @EventHandler
    fun onWorldChange(e: PlayerChangedWorldEvent) {
        if (e.player.isOp) return
        val creativeWorld = WorldManager.getWorld(e.player.world) ?: return
        e.player.gameMode = if (creativeWorld.getRights(e.player) < Rights.MEMBER) {
            creativeWorld.flags[this]
        } else {
            GameMode.CREATIVE
        }

    }
}