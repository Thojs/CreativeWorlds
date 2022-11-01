package nl.sagemc.creativeworlds.paper.utils.commandhandler

import me.thojs.kommandhandler.core.ArgumentParser
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

object OfflinePlayerParser: ArgumentParser<OfflinePlayer>() {
    override fun completions() = Bukkit.getOfflinePlayers().mapNotNull { it.name }.toTypedArray()

    override fun parse(string: String): OfflinePlayer? {
        return Bukkit.getOfflinePlayers().find { it.name == string }
    }
}