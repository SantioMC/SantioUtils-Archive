package me.santio.utils.database.builder.mongo

import me.santio.utils.database.adapter.impl.MongoAdapter
import me.santio.utils.database.builder.generic.TableBuilder
import org.bson.Document

class MongoTableBuilder(override val table: String, override val database: String): TableBuilder(table, database) {

    fun document(key: String): Document? {
        return MongoAdapter.getDocument(database, table, key)
    }

}