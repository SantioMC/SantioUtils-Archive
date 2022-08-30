@file:JvmName("MathUtils")
package me.santio.utils.math

import org.bukkit.Location
import kotlin.math.abs
import kotlin.math.floor

fun getGcd(a: Double, b: Double): Double {
    if (a < b) return getGcd(b, a)
    return if (abs(b) < 0.001) a else getGcd(b, a - floor(a / b) * b)
}

fun Double.almostEquals(number: Double): Boolean {
    return abs(this - number) < 0.00001
}

fun Double.rollingAverage(average: Double, sensitivity: Int): Double {
    return (( average * (sensitivity - 1) ) + this) / sensitivity
}

fun Location.horizontalDistance(location: Location): Double {
    val distX = this.x - location.x
    val distZ = this.z - location.z
    return (distX * distX) + (distZ * distZ)
}