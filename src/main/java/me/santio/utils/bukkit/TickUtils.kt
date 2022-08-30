@file:JvmName("TickUtils")
@file:Suppress("unused")

package me.santio.utils.bukkit

import kotlin.math.abs
import kotlin.math.floor

val currentTime: Int
    get() { return System.currentTimeMillis().toTicks() }

val started = System.currentTimeMillis()

fun Long.toTicks(): Int {
    return floor(((this - started)/50).toDouble()).toInt()
}

fun Int.toMS(): Long {
    return (this * 50).toLong()
}

fun Int.ticksSince(time: Int): Int {
    return abs(time - this)
}