package me.santio.utils.database

data class Storable<E>(
    val key: String,
    val value: E
)