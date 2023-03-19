package me.thojs.creativeworlds.paper.events

import me.thojs.creativeworlds.paper.worldmanager.CreativeWorld
import me.thojs.creativeworlds.paper.worldmanager.Rights
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class RightsChangeEvent(val player: Player, val world: CreativeWorld, val oldValue: Rights, val newValue: Rights) : Event() {
    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlerList
        }
    }
}