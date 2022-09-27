@file:Suppress("DuplicatedCode")

package me.santio.utils.database.adapter.impl

import me.santio.utils.SantioUtils
import me.santio.utils.database.adapter.DatabaseAdapter
import me.santio.utils.text.isSQLUnsafe
import java.sql.Connection
import java.sql.DriverManager

@Suppress("SqlNoDataSourceInspection", "SqlResolve")
object SQLiteAdapter: DatabaseAdapter {

    private var client: Connection? = null

    override fun connect(uri: String) {
        Class.forName("org.sqlite.JDBC")
        this.client = DriverManager.getConnection(uri)
    }

    override fun close() {
        this.client?.close()
        this.client = null
    }

    override fun <T> get(database: String, table: String, key: String, clazz: Class<T>): T? {
        if (isSQLUnsafe(database) || isSQLUnsafe(table) || isSQLUnsafe(key)) return null
        this.client?.schema = database
        val statement = this.client?.prepareStatement("SELECT value FROM $table WHERE key = ?") ?: return null
        statement.setString(1, key)

        val result = statement.executeQuery()
        if (!result.next()) return null

        val value = result.getString("value")
        return SantioUtils.GSON.fromJson(value, clazz)
    }

    override fun <T> set(database: String, table: String, key: String, value: T) {
        if (isSQLUnsafe(database) || isSQLUnsafe(table) || isSQLUnsafe(key)) return
        this.client?.schema = database
        createTable(table)
        val statement = this.client?.prepareStatement("INSERT INTO $table (key, value) VALUES (?, ?)") ?: return

        statement.setString(1, key)
        statement.setString(2, SantioUtils.GSON.toJson(value))

        statement.executeUpdate()
    }

    override fun delete(database: String, table: String, key: String) {
        if (isSQLUnsafe(database) || isSQLUnsafe(table) || isSQLUnsafe(key)) return
        this.client?.schema = database
        val statement = this.client?.prepareStatement("DELETE FROM $table WHERE key = ?") ?: return

        statement.setString(1, key)
        statement.executeUpdate()
    }

    override fun count(database: String, table: String): Int {
        if (isSQLUnsafe(database) || isSQLUnsafe(table)) return 0
        this.client?.schema = database
        val statement = this.client?.prepareStatement("SELECT COUNT(*) FROM $table") ?: return 0

        val result = statement.executeQuery()
        if (!result.next()) return 0

        return result.getInt(1)
    }

    private fun createTable(table: String) {
        val statement = this.client?.prepareStatement("CREATE TABLE IF NOT EXISTS $table (key TEXT PRIMARY KEY, value TEXT)")
        statement?.executeUpdate()
    }

    override fun getAsJson(database: String, table: String, key: String): String? {
        if (isSQLUnsafe(database) || isSQLUnsafe(table) || isSQLUnsafe(key)) return null
        this.client?.schema = database
        val statement = this.client?.prepareStatement("SELECT value FROM $table WHERE key = ?") ?: return null
        statement.setString(1, key)

        val result = statement.executeQuery()
        if (!result.next()) return null

        return result.getString("value")
    }
}