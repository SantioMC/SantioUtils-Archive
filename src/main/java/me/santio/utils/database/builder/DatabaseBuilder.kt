package me.santio.utils.database.builder

import me.santio.utils.database.builder.generic.TableBuilder

abstract class DatabaseBuilder {

    var database = "data"

    open fun table(table: String): TableBuilder = TableBuilder(table, database)

}