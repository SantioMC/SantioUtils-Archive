package me.santio.utils.item

import me.santio.utils.bukkit.Book
import me.santio.utils.text.colored
import me.santio.utils.text.normalcase
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import java.util.function.Consumer

@Suppress("unused")
class CustomItem @JvmOverloads constructor(
    private val material: Material,
    val name: String? = null,
    size: Int = 1
): ItemStack(material, size) {

    companion object {
        @JvmName("getSkull")
        @JvmStatic
        @JvmOverloads
        fun skull(player: OfflinePlayer, name: String? = null): CustomItem {
            return CustomItem(Material.PLAYER_HEAD, name).skull(player)
        }

        @JvmStatic
        fun fromItem(item: ItemStack): CustomItem {
            val i = CustomItem(item.type, null, item.amount)
            i.itemMeta = item.itemMeta
            return i
        }

        @JvmStatic
        fun book(): Book = Book()
    }

    init {
        this.name?.let { name(it) }
        size(size)
    }

    private var onClick: Consumer<InventoryClickEvent>? = null

    fun name() = name ?: material.name.normalcase()
    fun lore() = itemMeta?.lore ?: emptyList()
    fun size() = amount
    fun skull() = (itemMeta as SkullMeta?)?.owningPlayer
    fun onClick(event: InventoryClickEvent) = onClick?.accept(event) ?: run { event.isCancelled = true }

    fun color(): Color? {
        if (itemMeta is LeatherArmorMeta) return (itemMeta as LeatherArmorMeta).color
        else if (itemMeta is PotionMeta) return (itemMeta as PotionMeta).color
        return null
    }

    fun name(name: String): CustomItem {
        val meta = itemMeta ?: throw IllegalStateException("You can not rename air!")
        meta.setDisplayName(name.colored())
        itemMeta = meta
        return this
    }

    fun lore(vararg lore: String): CustomItem {
        val meta = itemMeta ?: throw IllegalStateException("You can not add lore to air!")
        meta.lore = lore.map { it.colored() }
        itemMeta = meta
        return this
    }

    fun size(size: Int): CustomItem {
        if (size < 1) return this
        amount = size
        return this
    }

    fun skull(player: OfflinePlayer): CustomItem {
        if (this.itemMeta !is SkullMeta) return this

        val meta = this.itemMeta as SkullMeta? ?: return this
        meta.owningPlayer = player
        itemMeta = meta

        return this
    }

    fun hideNBT(): CustomItem {
        toggleFlag(ItemFlag.HIDE_ATTRIBUTES, true)
        return this
    }

    @JvmOverloads
    fun toggleFlag(flag: ItemFlag, force: Boolean = false): CustomItem {
        val meta = itemMeta ?: throw IllegalStateException("You can not toggle flag on air!")
        if (meta.hasItemFlag(flag) && !force) meta.removeItemFlags(flag) else meta.addItemFlags(flag)
        itemMeta = meta
        return this
    }

    @JvmOverloads
    fun glowing(state: Boolean = true): CustomItem {
        val meta = itemMeta ?: throw IllegalStateException("You can not add glowing to air!")
        val enchantment = if (material == Material.BOW) Enchantment.PROTECTION_ENVIRONMENTAL else Enchantment.ARROW_INFINITE

        if (state) {
            meta.addEnchant(enchantment, 1, true)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        } else {
            meta.removeEnchant(enchantment)
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS)
        }

        itemMeta = meta
        return this
    }

    fun color(color: Color): CustomItem {
        val meta = itemMeta ?: throw IllegalStateException("You can not color air!")
        if (itemMeta !is LeatherArmorMeta && itemMeta !is PotionMeta) return this

        if (itemMeta is LeatherArmorMeta) (itemMeta as LeatherArmorMeta).setColor(color)
        else (itemMeta as PotionMeta).color = color

        itemMeta = meta
        return this
    }

    fun onClick(event: Consumer<InventoryClickEvent>): CustomItem {
        onClick = event
        return this
    }

}