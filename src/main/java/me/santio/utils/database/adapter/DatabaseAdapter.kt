package me.santio.utils.database.adapter

interface DatabaseAdapter {

    fun connect(uri: String)
    fun close()

    fun <T> get(database: String, table: String, key: String, clazz: Class<T>): T?
    fun <T> set(database: String, table: String, key: String, value: T)
    fun getAsJson(database: String, table: String, key: String): String?
    fun delete(database: String, table: String, key: String)
    fun count(database: String, table: String): Int

}