package nl.sagemc.creativeworlds.paper.utils

import org.bukkit.ChatColor

class ChatUtil {
    fun color(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }
}