package me.thojs.creativeworlds.paper.commands

import me.thojs.creativeworlds.paper.commands.world.*
import me.thojs.creativeworlds.paper.worldmanager.Rights
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.extension.commandBuilder
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.paper.util.sender.Source

object WorldCommand {
    private val arguments = listOf(
        AliasArgument,
        CreateArgument,
        DeleteArgument,
        DenyArgument,
        FlagArgument,
        HomeArgument,
        InfoArgument,
        MemberArgument,
        SetSpawnArgument,
        UnloadArgument,
        VisitArgument
    )

    fun register(manager: PaperCommandManager<Source>) {
        // TODO permissions
        arguments.forEach {
            val builder = manager.commandBuilder("world", aliases = arrayOf("w", "plot", "p")) {}
            it.register(builder)
        }
    }

    private fun testOwner(source: CommandSender): Boolean {
        return if (source is Player) {
            val world = WorldManager.getWorld(source.world) ?: return false
            return world.getRights(source) >= Rights.OWNER
        } else false
    }
}
