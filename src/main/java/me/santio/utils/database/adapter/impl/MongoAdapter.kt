package me.santio.utils.database.adapter.impl

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import com.mongodb.client.model.ReplaceOptions
import me.santio.utils.database.Storable
import me.santio.utils.database.adapter.DatabaseAdapter
import org.bson.UuidRepresentation
import org.litote.kmongo.KMongo
import org.litote.kmongo.*

object MongoAdapter: DatabaseAdapter {

    private var client: MongoClient? = null

    override fun connect(uri: String) {
        System.setProperty("org.litote.mongo.test.mapping.service", "org.litote.kmongo.jackson.JacksonClassMappingTypeService")
        this.client = KMongo.createClient(
            MongoClientSettings
                .builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(uri))
                .build()
        )
    }

    override fun close() {
        this.client?.close()
        this.client = null
    }

    override fun <T> get(database: String, table: String, key: String, clazz: Class<T>): T? {
        return this.client
            ?.getDatabase(database)
            ?.getCollection<Storable<T>>(table)
            ?.find(Filters.eq("key", key))
            ?.first()
            ?.value
    }

    override fun <T> set(database: String, table: String, key: String, value: T) {
        this.client
            ?.getDatabase(database)
            ?.getCollection<Storable<T>>(table)
            ?.replaceOne(
                Filters.eq("key", key),
                Storable(key, value),
                ReplaceOptions().upsert(true)
            )
    }

    override fun delete(database: String, table: String, key: String) {
        this.client
            ?.getDatabase(database)
            ?.getCollection(table)
            ?.deleteOne(Filters.eq("key", key))
    }

    override fun count(database: String, table: String): Int {
        return this.client
            ?.getDatabase(database)
            ?.getCollection(table)
            ?.countDocuments()?.toInt() ?: 0
    }

    override fun getAsJson(database: String, table: String, key: String): String? {
        return this.client
            ?.getDatabase(database)
            ?.getCollection(table)
            ?.find(Filters.eq("key", key))
            ?.first()
            ?.toJson()
    }

}