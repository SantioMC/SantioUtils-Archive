@file:JvmName("ChatUtils")
package me.santio.utils.text

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.entity.Player
import java.util.*
import java.util.regex.Matcher

private val hexPattern = Regex("&(#[a-fA-F\\d]{6})").toPattern()

fun String.colored(): String {
    val matcher: Matcher = hexPattern.matcher(ChatColor.translateAlternateColorCodes('&', this))
    val buffer = StringBuffer()

    while (matcher.find()) matcher.appendReplacement(buffer, ChatColor.of(matcher.group(1)).toString())

    return matcher.appendTail(buffer).toString()
}

fun String.strip(): String {
    return ChatColor.stripColor(this)
}

fun String.generateID(): String {
    return this.colored().strip().replace(" ".toRegex(), "_").lowercase()
}

fun String.toComponent(): BaseComponent {
    return TextComponent(*TextComponent.fromLegacyText(this))
}

fun BaseComponent.hover(text: String): BaseComponent {
    this.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(text))
    return this
}

fun BaseComponent.click(action: ClickEvent.Action, text: String): BaseComponent {
    this.clickEvent = ClickEvent(action, text)
    return this
}

fun BaseComponent.add(component: BaseComponent): BaseComponent {
    this.addExtra(component)
    return this
}

@Suppress("SpellCheckingInspection")
fun String.normalcase(): String {
    return this.split(" ", "-", "_").joinToString(" ") { s -> s.substring(0, 1).uppercase() + s.substring(1).lowercase() }
}

fun Player.sendMessage(message: Message) {
    this.spigot().sendMessage(message.component())
}