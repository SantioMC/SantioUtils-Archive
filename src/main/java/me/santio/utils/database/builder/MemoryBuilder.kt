package me.santio.utils.database.builder

import me.santio.utils.database.Database
import me.santio.utils.database.DatabaseType
import me.santio.utils.database.adapter.impl.SQLiteAdapter

class MemoryBuilder : DatabaseBuilder() {

    init {
        Database.switch(DatabaseType.SQLite, this)
        SQLiteAdapter.connect("jdbc:sqlite::memory:")
    }

    fun database(schema: String): MemoryBuilder {
        this.database = schema
        return this
    }

}