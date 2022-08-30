@file:JvmName("QueryUtils")
package me.santio.utils.query

import me.santio.utils.text.colored
import org.bukkit.entity.Player

fun Player.query(message: String): Query {
    return Query(this, message)
}