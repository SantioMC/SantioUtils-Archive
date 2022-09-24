package me.santio.utils.database.builder

import me.santio.utils.database.Database
import me.santio.utils.database.DatabaseType
import me.santio.utils.database.adapter.impl.MongoAdapter

class MongoBuilder(uri: String): DatabaseBuilder() {

    init {
        Database.switch(DatabaseType.MONGO, this)
        MongoAdapter.connect(uri)
    }

    fun database(schema: String): MongoBuilder {
        this.database = schema
        return this
    }

}