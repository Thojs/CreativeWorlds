package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.IntegerParser
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.CommandArgument
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HomeArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "home", LiteralParser("home")) {
    init {
        // (plot-id)
        argument(IntegerParser id "id") {
            executor {
                if (source !is Player) return@executor

                val index = it[1] as Int
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

        if (worlds.size < id) {
            player.sendMessage("Invalid plot provided. (${worlds.indices.first}, ${worlds.indices.last})")
            return
        }

        val world = worlds[id-1]

        world.load()
        world.bukkitWorld?.spawnLocation?.let { player.teleport(it) }
    }
}