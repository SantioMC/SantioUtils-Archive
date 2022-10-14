package me.santio.utils.database.builder

import me.santio.utils.database.Database
import me.santio.utils.database.DatabaseType
import me.santio.utils.database.adapter.impl.MongoAdapter
import me.santio.utils.database.builder.mongo.MongoTableBuilder

class MongoBuilder(uri: String): DatabaseBuilder() {

    init {
        Database.switch(DatabaseType.MONGO, this)
        MongoAdapter.connect(uri)
    }

    fun database(schema: String): MongoBuilder {
        this.database = schema
        return this
    }

    override fun table(table: String) = MongoTableBuilder(table, database)

}