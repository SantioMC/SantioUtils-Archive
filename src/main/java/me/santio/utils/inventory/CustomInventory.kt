package me.santio.utils.inventory

import me.santio.utils.SantioUtils
import me.santio.utils.item.CustomItem
import me.santio.utils.text.colored
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.function.Consumer
import java.util.function.Function

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class CustomInventory @JvmOverloads constructor(open val size: Int, var name: String = "Inventory") {
    constructor(inventory: CustomInventory): this(inventory.size, inventory.name)

    companion object {
        @JvmStatic
        fun getOpenInventory(player: Player): CustomInventory? {
            for (inventory in SantioUtils.inventories) {
                if (inventory.isOpen(player)) {
                    return inventory
                }
            }
            return null
        }

        @JvmStatic
        fun get(inventory: Inventory): CustomInventory? {
            for (customInventory in SantioUtils.inventories) {
                if (customInventory.inventory == inventory) {
                    return customInventory
                }
            }
            return null
        }
    }

    private var deleteOnClose: Boolean = true
    protected var inventory: Inventory = Bukkit.createInventory(null, size(), name.colored())
    protected var onClick: MutableMap<Int, Consumer<CustomInventoryClickEvent>> = mutableMapOf()
    internal var previous: CustomInventory? = null
    internal var next: CustomInventory? = null
    val id: String = UUID.randomUUID().toString()

    fun getBukkitInventory() = inventory
    fun rows(): Int = if (size % 9 == 0) size / 9 else size
    fun size(): Int = rows() * 9
    fun items(): List<ItemStack> = inventory.contents.toList()
    fun opened(): List<Player> = inventory.viewers.map { it as Player }
    fun isEmpty(ignore: Player? = null): Boolean = opened().none { it != ignore }

    fun isOpen(player: Player): Boolean = opened().contains(player)

    fun deleteOnClose(deleteOnClose: Boolean): CustomInventory {
        this.deleteOnClose = deleteOnClose
        return this
    }

    fun deleteOnClose() = deleteOnClose

    fun onClick(event: InventoryClickEvent) {
        onClick[event.slot]?.accept(CustomInventoryClickEvent(event, this)) ?: run { event.isCancelled = true }
    }

    open fun rename(name: String): CustomInventory {
        this.name = name

        val export = export()
        inventory = Bukkit.createInventory(null, size(), name.colored())

        import(export)
        open(*opened().toTypedArray())

        return this
    }

    @JvmOverloads
    fun set(
        slot: Int,
        item: ItemStack,
        onClick: Consumer<CustomInventoryClickEvent>? = null,
        keepEvent: Boolean = false
    ): CustomInventory {
        inventory.setItem(slot, item)
        if (onClick != null) this.onClick[slot] = onClick
        else if (!keepEvent) this.onClick.remove(slot)
        return this
    }

    @JvmOverloads
    fun set(
        slot: Slots,
        item: ItemStack,
        onClick: Consumer<CustomInventoryClickEvent>? = null
    ): CustomInventory {
        slot.apply(this).forEach { set(it, item, onClick) }
        return this
    }

    @JvmOverloads
    fun add(
        item: ItemStack,
        onClick: Consumer<CustomInventoryClickEvent>? = null
    ): CustomInventory {
        val slot = inventory.firstEmpty()
        if (slot == -1) return this

        set(slot, item, onClick)
        return this
    }

    fun clear(vararg slots: Int): CustomInventory {
        slots.forEach {
            inventory.setItem(it, null)
            onClick.remove(it)
        }

        return this
    }

    @JvmOverloads
    fun clear(slots: Slots = Slots.ALL): CustomInventory {
        slots.apply(this).forEach { clear(it) }
        return this
    }

    @JvmOverloads
    fun border(
        item: ItemStack,
        onClick: Consumer<CustomInventoryClickEvent> = Consumer { it.isCancelled = true }
    ): CustomInventory {
        val slots = Slots.column(1)
            .add(Slots.column(9))
            .add(Slots.row(1))
            .add(Slots.row(rows()))

        set(slots, item, onClick)
        return this
    }

    fun export(): List<SlotData> {
        val slots = mutableListOf<SlotData>()
        for (i in 0 until size()) {
            val item = inventory.getItem(i)
            if (item != null) {
                slots.add(SlotData(i, item, onClick[i]))
            }
        }

        return slots
    }

    fun import(slots: List<SlotData>): CustomInventory {
        slots.forEach { set(it.slot, it.item, it.clickEvent) }
        return this
    }

    fun open(vararg players: Player): CustomInventory {
        players.forEach {
            it.openInventory(inventory)
        }

        SantioUtils.inventories.add(this)
        return this
    }

    fun delete() {
        if (next != null) return
        SantioUtils.inventories.remove(this)
        opened().forEach { it.closeInventory() }
    }

    @JvmOverloads
    fun <T: Enum<T>> paginate(
        slots: Slots,
        clazz: Class<T>,
        handler: Function<T, CustomItem>,
        event: Function<T, Consumer<CustomInventoryClickEvent>> = Function { Consumer { it.isCancelled = true } },
        filter: ((T) -> Boolean)? = null
    ): PaginatedInventory {
        val paginatedInventory = PaginatedInventory(this)
        paginatedInventory.useSlots(slots)
        paginatedInventory.addItems(*clazz.enumConstants.filter(filter ?: { true }).map { handler.apply(it) }.toTypedArray())
        clazz.enumConstants.filter(filter ?: { true }).map { event.apply(it) }.forEachIndexed { index, consumer ->
            slots.get(index)?.let {
                paginatedInventory.onClick[it] = consumer
                paginatedInventory.setEvent(index / slots.size() + 1, it, consumer)
            }
        }
        return paginatedInventory
    }

    fun paginate(
        slots: Slots,
        items: List<CustomItem>,
    ): PaginatedInventory {
        val paginatedInventory = PaginatedInventory(this)
        paginatedInventory.useSlots(slots)
        paginatedInventory.addItems(*items.toTypedArray())

        items.forEachIndexed { index, item ->
            val page = index / slots.size()
            paginatedInventory.setEvent(page, index % slots.size()) {
                item.onClick(it)
            }
        }

        return paginatedInventory
    }

}