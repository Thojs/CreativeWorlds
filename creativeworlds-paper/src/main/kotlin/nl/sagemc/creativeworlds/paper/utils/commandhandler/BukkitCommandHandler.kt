package nl.sagemc.creativeworlds.paper.utils.commandhandler

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import nl.sagemc.creativeworlds.api.commandhandler.CommandHandler
import org.bukkit.command.*

class BukkitCommandHandler(plugin: Plugin) : CommandHandler<CommandSender>({ command ->
    // Command Executor
    val executor = object : CommandExecutor, TabCompleter {
        override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<String>): Boolean {
            try {
                command.execute(p0, p3)
            } catch (ex: CommandException) {
                //todo SEND ERROR TO PLAYER
            }
            return true
        }

        override fun onTabComplete(p0: CommandSender, p1: Command, p2: String, p3: Array<String>): MutableList<String> {
            return command.suggestions(p0, p3).toMutableList()
        }
    }

    // Register Command
    val com = PluginCommand::class.java.getDeclaredConstructor(String::class.java, Plugin::class.java)
    com.isAccessible = true

    val bCommand = com.newInstance(command.name, plugin)
    bCommand.aliases = command.aliases.toList()
    bCommand.setExecutor(executor)

    val field = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
    field.isAccessible = true
    val commandMap = field.get(Bukkit.getServer()) as CommandMap
    commandMap.register(command.name, plugin.name, bCommand)
})