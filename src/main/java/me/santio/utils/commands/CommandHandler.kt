package me.santio.utils.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.util.Consumer
import java.lang.reflect.Field

object CommandHandler {

    @JvmStatic
    var defaultPermissionMessage = "&cYou are not permitted to do this!"

    @JvmStatic
    fun simple(name: String, execute: Consumer<CommandData>) {
        create(CommandBuilder(name).onExecute(execute))
    }

    @JvmStatic
    fun create(vararg builders: CommandBuilder) {
        builders.forEach { builder -> builder.build().register(getCommandMap()) }
    }

    @JvmStatic
    fun defaultPermissionMessage(message: String) {
        defaultPermissionMessage = message
    }

    private fun getCommandMap(): CommandMap {
        val bukkitCommandMap: Field = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
        bukkitCommandMap.isAccessible = true

        return bukkitCommandMap.get(Bukkit.getServer()) as CommandMap
    }

}