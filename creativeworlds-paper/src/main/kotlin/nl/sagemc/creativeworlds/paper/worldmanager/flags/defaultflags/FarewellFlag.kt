package nl.sagemc.creativeworlds.paper.worldmanager.flags.defaultflags

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import nl.sagemc.creativeworlds.paper.worldmanager.flags.Flag

class FarewellFlag : Flag<Component>("farewell", Component.empty()) {
    override fun serialize(obj: Component): String {
        return MiniMessage.miniMessage().serialize(obj)
    }

    override fun deserialize(obj: String): Component {
        return MiniMessage.miniMessage().deserialize(obj)
    }
}