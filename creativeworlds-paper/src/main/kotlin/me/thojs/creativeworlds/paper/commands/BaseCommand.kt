package me.thojs.creativeworlds.paper.commands

import me.thojs.creativeworlds.paper.commands.exceptions.CannotEditWorldException
import me.thojs.creativeworlds.paper.worldmanager.CreativeWorld
import me.thojs.creativeworlds.paper.worldmanager.Rights
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.Source
import org.incendo.cloud.permission.Permission

abstract class BaseCommand(private val name: String) {
    open val permission: Permission = Permission.EMPTY
    protected abstract fun build(builder: MutableCommandBuilder<Source>)

    fun register(builder: MutableCommandBuilder<Source>) {
        builder.apply {
            literal(name)
            permission(this@BaseCommand.permission)
            build(this)
        }
    }

    fun testWorldRights(world: CreativeWorld, player: Player) {
        if (world.getRights(player) >= Rights.OWNER) return
        throw CannotEditWorldException()
    }
}