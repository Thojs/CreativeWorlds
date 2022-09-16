package nl.sagemc.creativeworlds.paper.utils.commandhandler

import nl.sagemc.creativeworlds.api.commandhandler.ArgumentParser
import org.bukkit.Bukkit
import org.bukkit.World

object WorldParser : ArgumentParser<World>() {
    override fun parse(string: String) = Bukkit.getWorld(string)

    override fun completions() = Bukkit.getWorlds().map { it.name }.toTypedArray()
}