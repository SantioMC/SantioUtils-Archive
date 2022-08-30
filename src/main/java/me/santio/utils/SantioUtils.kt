package me.santio.utils

import me.santio.utils.inventory.CustomInventory
import me.santio.utils.inventory.InventoryListener
import me.santio.utils.query.QueryListener
import org.bukkit.plugin.java.JavaPlugin

class SantioUtils(plugin: JavaPlugin) {

    companion object {
        val inventories: MutableSet<CustomInventory> = mutableSetOf()
    }

    init {
        plugin.server.pluginManager.registerEvents(InventoryListener, plugin)
        plugin.server.pluginManager.registerEvents(QueryListener, plugin)
    }

}