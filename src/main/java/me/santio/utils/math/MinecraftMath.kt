package me.santio.utils.math

import kotlin.math.PI

object MinecraftMath {
    private val SIN_TABLE = FloatArray(65536)

    init {
        for (i in 0 until 65536) {
            SIN_TABLE[i] = kotlin.math.sin(i.toDouble() * PI * 2.0 / 65536.0).toFloat()
        }
    }

    fun sin(value: Float): Float {
        return SIN_TABLE[(value * 10430.378f).toInt() and 65535]
    }

    fun cos(value: Float): Float {
        return SIN_TABLE[(value * 10430.378f + 16384.0f).toInt() and 65535]
    }
}