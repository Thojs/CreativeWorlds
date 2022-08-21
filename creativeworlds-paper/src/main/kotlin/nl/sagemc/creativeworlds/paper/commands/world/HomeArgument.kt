package nl.sagemc.creativeworlds.paper.commands.world

import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.IntegerParser
import nl.sagemc.creativeworlds.api.commandhandler.defaultparsers.LiteralParser
import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object HomeArgument : Command.CommandArgument<CommandSender>(LiteralParser("home")) {
    init {
        // (plot-id)
        argument(IntegerParser) {
            execute { source, arguments ->
                if (source !is Player) return@execute

                val index = arguments[1] as Int
                goToPlot(source, index)
            }
        }

        execute { source, _ ->
            if (source !is Player) return@execute
            goToPlot(source, 1)
        }
    }

    private fun goToPlot(player: Player, id: Int) {
        val worlds = WorldManager.getWorlds(player.uniqueId)

        if (worlds.size < id) {
            player.sendMessage("Invalid plot provided. (${worlds.indices.first}, ${worlds.indices.last})")
            return
        }

        val world = worlds[id-1]

        world.load()
        world.bukkitWorld?.spawnLocation?.let { player.teleport(it) }
    }
}