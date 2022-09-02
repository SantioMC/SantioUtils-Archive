package me.santio.utils.commands

import org.bukkit.command.CommandSender

data class CommandData(
    val sender: CommandSender,
    val label: String,
    val args: MutableList<String>
)
