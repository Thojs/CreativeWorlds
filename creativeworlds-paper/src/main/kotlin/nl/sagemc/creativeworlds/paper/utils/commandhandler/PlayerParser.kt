package nl.sagemc.creativeworlds.paper.utils.commandhandler

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object PlayerParser : ArgumentParser<Player>() {
    override fun parse(string: String) = Bukkit.getPlayer(string)

    override fun completions() = Bukkit.getOnlinePlayers().map { it.name }.toTypedArray()
}