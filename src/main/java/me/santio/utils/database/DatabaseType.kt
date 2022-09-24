package me.santio.utils.database

import me.santio.utils.database.adapter.DatabaseAdapter
import me.santio.utils.database.adapter.impl.MongoAdapter
import me.santio.utils.database.adapter.impl.SQLiteAdapter

enum class DatabaseType(val adapter: DatabaseAdapter?) {

    NONE(null),
    MONGO(MongoAdapter),
    SQLite(SQLiteAdapter),
    ;

}