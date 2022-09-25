package me.santio.utils.inventory

import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

data class SlotData(
    val slot: Int,
    val item: ItemStack,
    val clickEvent: Consumer<CustomInventoryClickEvent>?
)
