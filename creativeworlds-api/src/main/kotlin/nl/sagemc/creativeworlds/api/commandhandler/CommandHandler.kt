package nl.sagemc.creativeworlds.api.commandhandler

open class CommandHandler<T: Any>(private val onRegister: (command: Command<T>) -> Unit) {
    val registeredCommands: MutableList<Command<T>> = ArrayList()

    fun registerCommands(vararg commands: Command<T>) {
        commands.forEach(onRegister)
        registeredCommands.addAll(commands)
    }
}