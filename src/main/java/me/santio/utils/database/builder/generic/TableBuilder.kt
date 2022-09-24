package me.santio.utils.database.builder.generic

import me.santio.utils.database.Database

class TableBuilder(
    val table: String,
    val database: String = "data"
) {

    fun <T> set(key: String, value: T) {
        Database.adapter().adapter?.set(database, table, key, value)
    }

    fun <T> get(key: String, clazz: Class<T>): T? {
        return Database.adapter().adapter?.get(database, table, key, clazz)
    }

    fun delete(key: String) {
        Database.adapter().adapter?.delete(database, table, key)
    }

    fun count(): Int {
        return Database.adapter().adapter?.count(database, table) ?: 0
    }

}