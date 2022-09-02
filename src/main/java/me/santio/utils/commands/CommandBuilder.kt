package me.santio.utils.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.util.Consumer

@Suppress("unused")
class CommandBuilder(val name: String) {

    private val aliases: MutableList<String> = mutableListOf()
    private val executors: MutableList<Consumer<CommandData>> = mutableListOf()
    private var description: String = "No description provided"
    private var permission: String? = null
    private var permissionMessage: String? = null

    fun aliases(vararg aliases: String): CommandBuilder {
        this.aliases.addAll(aliases)
        return this
    }

    fun description(description: String): CommandBuilder {
        this.description = description
        return this
    }

    fun permission(permission: String): CommandBuilder {
        this.permission = permission
        return this
    }

    fun onExecute(executor: Consumer<CommandData>): CommandBuilder {
        this.executors.add(executor)
        return this
    }

    fun build(): Command {
        return object : Command(name, description, "/$name", aliases) {
            override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
                if (permission != null && !sender.hasPermission(permission!!)) {
                    sender.sendMessage(permissionMessage ?: CommandHandler.defaultPermissionMessage)
                    return true
                }
                executors.forEach { it.accept(CommandData(sender, label, args.toMutableList())) }
                return true
            }
        }
    }

    fun create() {
        CommandHandler.create(this)
    }

}