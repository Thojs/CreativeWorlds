package nl.sagemc.creativeworlds.paper.commands.world

import me.thojs.kommandhandler.bukkit.parsers.OfflinePlayerParser
import me.thojs.kommandhandler.core.CommandArgument
import me.thojs.kommandhandler.core.parsers.IntegerParser
import me.thojs.kommandhandler.core.parsers.LiteralParser
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import nl.sagemc.creativeworlds.paper.CreativeWorlds
import nl.sagemc.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VisitArgument(source: CommandSender) : CommandArgument<CommandSender, String>(source, "visit", LiteralParser("visit")) {
    init {
        argument(OfflinePlayerParser id "target") {
            val target = this
            argument(IntegerParser id "id") {
                executor {
                    if (source !is Player) return@executor

                    val player = it[target] ?: return@executor
                    val id = it[this] ?: return@executor

                    visitWorld(source, player, id)
                }
            }

            executor {
                if (source !is Player) return@executor

                val player = it[this] ?: return@executor

                visitWorld(source, player, 1)
            }
        }
    }

    fun visitWorld(visitor: Player, player: OfflinePlayer, id: Int) {
        val worlds = WorldManager.getWorlds(player)

        if (worlds.isEmpty()) {
            visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("No worlds found from ${player.name}.").color(NamedTextColor.RED)))
            return
        }

        val world = worlds.getOrNull(id-1)

        if (world == null) {
            visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("No world found with id $id.").color(NamedTextColor.RED)))
            return
        }

        if (world.denied.contains(visitor) && !player.isOp) {
            visitor.sendMessage(CreativeWorlds.prefix.append(Component.text("You are denied from this plot.").color(NamedTextColor.RED)))
            return
        }

        world.load()
        world.bukkitWorld?.spawnLocation?.let { visitor.teleport(it) }
    }
}