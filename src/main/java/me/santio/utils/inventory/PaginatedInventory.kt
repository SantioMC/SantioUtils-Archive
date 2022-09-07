package me.santio.utils.inventory

import me.santio.utils.SantioUtils
import me.santio.utils.item.CustomItem
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.function.BiConsumer
import java.util.function.Consumer
import kotlin.math.ceil

@Suppress("MemberVisibilityCanBePrivate")
class PaginatedInventory(
    inv: CustomInventory
): CustomInventory(inv.rows(), inv.name) {

    init {
        import(inv.export())
        inv.delete()
        SantioUtils.inventories.add(this)
    }

    private var slots: Slots = Slots.ALL
    private var items: MutableList<ItemStack> = mutableListOf()
    private var events: MutableMap<Int, MutableMap<Int, Consumer<InventoryClickEvent>>> = mutableMapOf()
    private var switchConsumers: MutableList<BiConsumer<PaginatedInventory, Int>> = mutableListOf()

    private var back: Pair<Slots, ItemStack>? = null
    private var forward: Pair<Slots, ItemStack>? = null

    override fun isOpen(player: Player): Boolean = player.hasMetadata("inventory")
            && player.getMetadata("inventory")[0].asString() == id

    @JvmOverloads
    fun open(plugin: JavaPlugin, player: Player, page: Int = 1): PaginatedInventory {
        SantioUtils.inventories.add(this)
        player.setMetadata("inventory", FixedMetadataValue(plugin, this))
        player.openInventory(this.inventory)

        // Paginate items
        val paginatedItems = items.subList((page - 1) * slots.size(), (page * slots.size()).coerceAtMost(items.size))
        paginatedItems.forEachIndexed { index, item -> slots.get(index)?.let { set(it, item) }}
        onClick = events[page] ?: mutableMapOf()

        // Add back and forward buttons
        if (page > 1) back?.let { set(it.first, it.second) { e ->
            e.isCancelled = true
            open(plugin, e.whoClicked as Player, page - 1)
        }} else back?.let { set(it.first, CustomItem.fromItem(it.second).name("&cFirst Page")) }

        if (page < pages()) forward?.let { set(it.first, it.second) { e ->
            e.isCancelled = true
            open(plugin, e.whoClicked as Player, page + 1)
        }} else forward?.let { set(it.first, CustomItem.fromItem(it.second).name("&cLast Page")) }

        return this
    }

    fun pages() = ceil(items.size / slots.size().toDouble()).toInt()

    fun useSlots(slots: Slots): PaginatedInventory {
        this.slots = slots
        return this
    }

    fun addItems(vararg items: ItemStack): PaginatedInventory {
        this.items.addAll(items)
        return this
    }

    fun setEvent(page: Int, slot: Int, event: Consumer<InventoryClickEvent>): PaginatedInventory {
        events.getOrPut(page) { mutableMapOf() }[slot] = event
        return this
    }

    fun addBackButton(slots: Slots, item: ItemStack): PaginatedInventory {
        back = Pair(slots, item)
        return this
    }

    fun addForwardButton(slots: Slots, item: ItemStack): PaginatedInventory {
        forward = Pair(slots, item)
        return this
    }

    fun addBackButton(slot: Int, item: ItemStack): PaginatedInventory {
        return addBackButton(Slots.get(slot), item)
    }

    fun addForwardButton(slot: Int, item: ItemStack): PaginatedInventory {
        return addForwardButton(Slots.get(slot), item)
    }

    fun onPageChange(page: BiConsumer<PaginatedInventory, Int>): PaginatedInventory {
        switchConsumers.add(page)
        return this
    }

}