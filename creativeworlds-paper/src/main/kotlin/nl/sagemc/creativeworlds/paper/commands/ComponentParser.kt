package nl.sagemc.creativeworlds.paper.commands

import me.thojs.kommandhandler.core.ArgumentParser
import net.kyori.adventure.text.Component
import nl.sagemc.creativeworlds.paper.utils.Utils.miniMessage

object ComponentParser : ArgumentParser<Component> {
    override fun completions(): Array<String> {
        return emptyArray()
    }

    override fun parse(string: String): Component {
        return miniMessage(string)
    }
}