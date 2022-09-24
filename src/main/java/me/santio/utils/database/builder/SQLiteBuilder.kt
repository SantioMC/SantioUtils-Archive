package me.santio.utils.database.builder

import me.santio.utils.database.Database
import me.santio.utils.database.DatabaseType
import me.santio.utils.database.adapter.impl.MongoAdapter
import me.santio.utils.database.adapter.impl.SQLiteAdapter
import java.io.File

class SQLiteBuilder(file: File): DatabaseBuilder() {

    init {
        Database.switch(DatabaseType.MONGO, this)
        file.mkdirs()
        SQLiteAdapter.connect(file.absolutePath)
    }

    fun database(schema: String): SQLiteBuilder {
        this.database = schema
        return this
    }

}