package me.santio.utils

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.santio.utils.adapters.UUIDAdapter
import me.santio.utils.inventory.CustomInventory
import me.santio.utils.inventory.InventoryListener
import me.santio.utils.query.QueryListener
import org.bukkit.plugin.Plugin
import java.util.*

class SantioUtils(plugin: Plugin) {

    companion object {
        val GSON: Gson = GsonBuilder().registerTypeAdapter(UUID::class.java, UUIDAdapter()).create()
        val inventories: MutableSet<CustomInventory> = mutableSetOf()

        var plugin: Plugin? = null

        fun register(plugin: Plugin) {
            if (this.plugin != null) return
            this.plugin = plugin
        }
    }

    init {
        plugin.server.pluginManager.registerEvents(InventoryListener, plugin)
        plugin.server.pluginManager.registerEvents(QueryListener, plugin)
    }

}