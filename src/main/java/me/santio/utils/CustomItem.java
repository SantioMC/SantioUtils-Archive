package me.santio.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public final class CustomItem extends ItemStack {
    
    public CustomItem(Material material) {
        super(material);
    }
    public CustomItem(Material material, int amount) {
        super(material, amount);
    }
    
    public CustomItem setDisplayName(String name) {
        ItemMeta meta = this.getItemMeta();
        if (meta == null) return this;
        meta.setDisplayName(ChatUtils.tacc(name));
        this.setItemMeta(meta);
        return this;
    }
    
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public CustomItem setItemLore(List<String> lore) {
        ItemMeta meta = this.getItemMeta();
        if (meta == null) return this;
        ArrayList<String> formattedLore = new ArrayList<>();
        for (String line : lore) formattedLore.add(ChatColor.translateAlternateColorCodes('&', line));
        meta.setLore(formattedLore);
        this.setItemMeta(meta);
        return this;
    }
    
    public CustomItem setLore(List<String> lore) {
        return setItemLore(lore);
    }
    
    public CustomItem setItemLore(String... lore) {
        return setItemLore(Arrays.asList(lore));
    }
    
    public CustomItem setLore(String... lore) {
        return setItemLore(Arrays.asList(lore));
    }
    
    public List<String> getItemLore() {
        if (getItemMeta() == null) return new ArrayList<>();
        else return getItemMeta().getLore() == null ? new ArrayList<>() : getItemMeta().getLore();
    }
    
    public List<String> getLore() {
        return getItemLore();
    }
    
    public CustomItem setHeadOwner(OfflinePlayer player) {
        if (!(this.getItemMeta() instanceof SkullMeta)) return this;
        SkullMeta meta = (SkullMeta) this.getItemMeta();
        if (meta == null) return this;
        meta.setOwningPlayer(player);
        this.setItemMeta(meta);
        return this;
    }
    
    public CustomItem setGlowing(boolean glowing) {
        ItemMeta meta = this.getItemMeta();
        if (meta == null) return this;
        
        if (!glowing) {
            meta.removeEnchant(Enchantment.DURABILITY);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        
        this.setItemMeta(meta);
        return this;
    }
    
    public CustomItem setLeatherColor(int red, int green, int blue) {
        ItemMeta meta = this.getItemMeta();
        if (meta == null) return this;
    
        ((LeatherArmorMeta) meta).setColor(Color.fromRGB(red, green, blue));
        setItemMeta(meta);
        return this;
    }
    
    public CustomItem setPotionColor(int red, int green, int blue) {
        ItemMeta meta = getItemMeta();
        if (meta == null) return this;
    
        ((PotionMeta) meta).setColor(Color.fromRGB(red, green, blue));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        setItemMeta(meta);
        return this;
    }
    
}
