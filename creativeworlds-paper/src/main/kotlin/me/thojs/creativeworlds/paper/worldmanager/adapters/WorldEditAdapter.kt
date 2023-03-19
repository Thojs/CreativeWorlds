package me.thojs.creativeworlds.paper.worldmanager.adapters

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.event.extent.EditSessionEvent
import com.sk89q.worldedit.extent.NullExtent
import com.sk89q.worldedit.util.eventbus.Subscribe
import me.thojs.creativeworlds.paper.worldmanager.Rights
import me.thojs.creativeworlds.paper.worldmanager.WorldManager
import org.bukkit.Bukkit

object WorldEditAdapter {
    init {
        WorldEdit.getInstance().eventBus.register(object {
            @Subscribe
            fun onEditSessionEvent(event: EditSessionEvent) {
                if (event.actor?.isPlayer == true) {
                    val bukkitPlayer = Bukkit.getPlayer(event.actor?.uniqueId ?: return) ?: return
                    val world = WorldManager.getWorld(bukkitPlayer.world) ?: return
                    if (world.getRights(bukkitPlayer) >= Rights.TRUSTEE) {
                        return
                    } else {
                        event.extent = NullExtent()
                    }
                }
            }
        })
    }
}