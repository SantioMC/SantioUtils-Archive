package me.santio.utils

import me.santio.utils.inventory.CustomInventory
import me.santio.utils.inventory.InventoryListener
import me.santio.utils.query.QueryListener
import org.bukkit.plugin.Plugin
import java.util.*

class SantioUtils(plugin: Plugin) {

    companion object {
        val inventories: MutableSet<CustomInventory> = mutableSetOf()
        val switching: MutableSet<UUID> = mutableSetOf()
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