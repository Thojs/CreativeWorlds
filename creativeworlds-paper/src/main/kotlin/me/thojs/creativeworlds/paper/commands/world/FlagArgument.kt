package me.thojs.creativeworlds.paper.commands.world

import me.thojs.creativeworlds.paper.commands.BaseCommand
import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.incendo.cloud.paper.util.sender.Source

object FlagArgument : BaseCommand("flag") {
    override fun build(builder: MutableCommandBuilder<Source>) {
//        TODO("Not yet implemented")
    }

//        if (source is Player) {
//            val world = WorldManager.getWorld(source.world)
//
//            world?.flags?.getCommandFlags()?.forEach { flag ->
//                argument(LiteralParser(flag.name)) {
//                    argument(LiteralParser("set")) {
//                        arguments += flag.createArgument(source, world)
//                    }
//
//                    argument(LiteralParser("reset")) {
//                        executor {
//                            world.flags.reset(flag)
//                            source.sendMessage(CreativeWorlds.prefix.append(Component.text("Successfully set flag `${flag.name}` to it's default value.").color(NamedTextColor.GREEN)))
//                        }
//                    }
//                }
//            }
//        }
}