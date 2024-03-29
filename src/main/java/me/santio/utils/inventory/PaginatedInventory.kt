package me.santio.utils.inventory

import me.santio.utils.SantioUtils
import me.santio.utils.item.CustomItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.function.BiConsumer
import java.util.function.Consumer
import kotlin.math.ceil

@Suppress("MemberVisibilityCanBePrivate", "unused")
class PaginatedInventory(
    inv: CustomInventory
): CustomInventory(inv.rows(), inv.name) {

    private val export: List<SlotData>

    init {
        export = inv.export()
        import(export)
        inv.delete()
        SantioUtils.inventories.add(this)
    }

    private var slots: Slots = Slots.ALL
    private var items: MutableList<ItemStack> = mutableListOf()
    var events: MutableMap<Int, MutableMap<Int, Consumer<CustomInventoryClickEvent>>> = mutableMapOf()
    private var page: MutableMap<UUID, Int> = mutableMapOf()
    private var switchConsumers: MutableList<BiConsumer<PaginatedInventory, Int>> = mutableListOf()

    private var back: Pair<Slots, ItemStack>? = null
    private var forward: Pair<Slots, ItemStack>? = null

    @JvmOverloads
    fun open(player: Player, page: Int = 1): PaginatedInventory {
        player.openInventory(this.inventory)
        this.page[player.uniqueId] = page

        // Paginate items
        onClick = events[page] ?: mutableMapOf()
        val paginatedItems = items.subList((page - 1) * slots.size(), (page * slots.size()).coerceAtMost(items.size))
        paginatedItems.forEachIndexed { index, item -> slots.get(index)?.let { set(it, item, keepEvent = true)}}

        // Add back and forward buttons
        if (page > 1) back?.let { set(it.first, it.second) { e ->
            e.isCancelled = true
            switch(page - 1)
            open(e.whoClicked as Player, page - 1)
        }} else back?.let { set(it.first, CustomItem.fromItem(it.second).name("&cFirst Page")) }

        if (page < pages()) forward?.let { set(it.first, it.second) { e ->
            e.isCancelled = true
            switch(page + 1)
            open(e.whoClicked as Player, page + 1)
        }} else forward?.let { set(it.first, CustomItem.fromItem(it.second).name("&cLast Page")) }

        return this
    }

    fun pages() = ceil(items.size / slots.size().toDouble()).toInt()
    fun page(player: Player) = page[player.uniqueId] ?: 1
    fun page(player: UUID) = page[player] ?: 1

    fun useSlots(slots: Slots): PaginatedInventory {
        this.slots = slots
        return this
    }

    fun addItems(vararg items: ItemStack): PaginatedInventory {
        this.items.addAll(items)
        return this
    }

    fun setEvent(page: Int, slot: Int, event: Consumer<CustomInventoryClickEvent>): PaginatedInventory {
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

    private fun switch(page: Int) {
        switchConsumers.forEach { it.accept(this, page) }
    }

}