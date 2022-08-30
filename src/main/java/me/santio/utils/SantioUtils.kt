package me.santio.utils

import me.santio.utils.bukkit.AsyncUtils
import me.santio.utils.inventory.CustomInventory
import me.santio.utils.inventory.InventoryListener
import me.santio.utils.query.QueryListener
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class SantioUtils(plugin: JavaPlugin) {

    companion object {
        val inventories: MutableSet<CustomInventory> = mutableSetOf()
        val switching: MutableSet<UUID> = mutableSetOf()
    }

    init {
        plugin.server.pluginManager.registerEvents(InventoryListener, plugin)
        plugin.server.pluginManager.registerEvents(QueryListener, plugin)
    }

}