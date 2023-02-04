package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.IntegerParser
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HomeArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, LiteralParser("home", "h")) {
    init {
        // (plot-id)
        argument(IntegerParser id "id") {
            executor {
                if (source !is Player) return@executor

                val index = it[this]
                goToPlot(source, index)
            }
        }

        executor {
            if (source !is Player) return@executor
            goToPlot(source, 1)
        }
    }

    private fun goToPlot(player: Player, id: Int) {
        val worlds = WorldManager.getWorlds(player)

        if (id < 1 || worlds.size < id) {
            player.sendMessage(CreativeWorlds.prefix.append(Component.text("Invalid plot id provided. (${worlds.indices.first+1}, ${worlds.indices.last+1})").color(NamedTextColor.RED)))
            return
        }

        val world = worlds[id-1]

        if (world.bukkitWorld == null) {
            source.sendMessage(CreativeWorlds.prefix.append(Component.text("Loading world, we will teleport you when the world has been loaded.").color(NamedTextColor.GREEN)))
        } else {
            source.sendMessage(CreativeWorlds.prefix.append(Component.text("Teleporting you to the world.").color(NamedTextColor.GREEN)))
        }
        world.load()
        world.bukkitWorld?.spawnLocation?.let { player.teleport(it) }
    }
}