package me.thojs.creativeworlds.paper.worldmanager.flags.defaultflags

import me.thojs.kommandhandler.core.parsers.BooleanParser
import me.thojs.creativeworlds.paper.worldmanager.CreativeWorld
import me.thojs.creativeworlds.paper.worldmanager.flags.CommandFlag
import org.bukkit.configuration.ConfigurationSection

object VehicleUseFlag: CommandFlag<Boolean>("vehicle-use", false, BooleanParser id "allow") {
    override fun serialize(obj: Boolean, section: ConfigurationSection) {
        section["value"] = obj
    }

    override fun deserialize(obj: ConfigurationSection): Boolean {
        return obj.getBoolean("value")
    }

    override fun onChange(world: CreativeWorld, newValue: Boolean) {}
}