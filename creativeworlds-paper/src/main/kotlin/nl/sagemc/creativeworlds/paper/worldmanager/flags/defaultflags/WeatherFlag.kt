package nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags

import nl.sagemc.creativeworlds.paper.worldmanager.CreativeWorld
import nl.sagemc.creativeworlds.paper.worldmanager.flags.Flag

object WeatherFlag: Flag<WeatherType>("weather", WeatherType.CLEAR) {
    override fun serialize(obj: WeatherType) = obj.toString()

    override fun deserialize(obj: String): WeatherType? {
        return try {
            WeatherType.valueOf(obj.uppercase())
        } catch (e: Exception) {
            null
        }
    }

    override fun onChange(world: CreativeWorld, newValue: WeatherType) {
        world.bukkitWorld?.isThundering = newValue == WeatherType.THUNDER
        world.bukkitWorld?.setStorm(newValue != WeatherType.CLEAR)
    }
}

enum class WeatherType {
    CLEAR,
    RAIN,
    THUNDER
}