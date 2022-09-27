package me.santio.utils.database

import me.santio.utils.database.builder.DatabaseBuilder
import me.santio.utils.database.builder.MemoryBuilder
import me.santio.utils.database.builder.MongoBuilder
import me.santio.utils.database.builder.SQLiteBuilder
import java.io.File

object Database {

    private var adapter: DatabaseType = DatabaseType.NONE
    private var builder: DatabaseBuilder? = null

    internal fun switch(adapter: DatabaseType, builder: DatabaseBuilder) {
        if (this.adapter != DatabaseType.NONE) this.adapter.adapter?.close()
        this.adapter = adapter
        this.builder = builder
    }

    @JvmStatic
    fun adapter() = adapter

    @JvmStatic
    fun mongo(uri: String): MongoBuilder = builder as MongoBuilder? ?: MongoBuilder(uri)

    @JvmStatic
    fun mongo(): MongoBuilder? = builder as MongoBuilder?

    @JvmStatic
    fun sqlite(file: File): SQLiteBuilder = builder as SQLiteBuilder? ?: SQLiteBuilder(file)

    @JvmStatic
    fun sqlite(): SQLiteBuilder? = builder as SQLiteBuilder?

    @JvmStatic
    fun memory(): MemoryBuilder = builder as MemoryBuilder? ?: MemoryBuilder()

}