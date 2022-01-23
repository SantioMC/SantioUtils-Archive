package me.santio.utils.inventories;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.santio.utils.ChatUtils;
import me.santio.utils.SantioUtils;
import me.santio.utils.inventories.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unused", "StringConcatenationInLoop", "UnusedReturnValue"})
@Accessors(chain = true)
public final class CustomInventory {
    @Getter private final SantioUtils utils;
    
    @Setter @Getter private boolean deleteOnClose = true;
    @Getter private final int size;
    @Getter private final String name;
    @Getter private final Inventory inventory;
    @Getter private String ID;
    
    @Getter private final HashMap<String, Consumer<InventoryClickEvent>> events = new HashMap<>();
    
    public CustomInventory(int size, String name, SantioUtils utils) {
        this.utils = utils;
        
        this.size = (size % 9 == 0) ? size : 9 * size;
        this.name = name;
        this.ID = ChatUtils.toID(name);
        while (utils.getInventories().containsKey(getID())) this.ID += "_1";
        
        inventory = Bukkit.createInventory(null, this.size, ChatUtils.tacc(name));
        utils.getInventories().put(getID(), this);
    }
    
    public CustomInventory open(Player... players) {
        for (Player player : players) open(player);
        return this;
    }
    
    public CustomInventory open(Player player) {
        player.openInventory(getInventory());
        player.setMetadata("sutils_inventory_open", new FixedMetadataValue(utils.getPlugin(), getID()));
        return this;
    }
    
    public boolean isOpen(Player player) {
        return player.hasMetadata("sutils_inventory_open") && (player.getMetadata("sutils_inventory_open").get(0).asString().equals(getID()));
    }
    
    public void delete() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isOpen(player)) player.closeInventory();
        }
        events.clear();
        utils.getInventories().remove(getID());
    }
    
    public CustomInventory setItem(int slot, Button item) {
        return setItem(slot, item, (e) -> {
            e.setCancelled(true);
            item.onClick(e);
            updateLore(slot, item.getCompleteLore());
        });
    }
    
    public CustomInventory updateLore(int slot, List<String> lore) {
        ItemStack item = inventory.getItem(slot);
        if (item == null) return this;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        
        meta.setLore(lore);
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
        return this;
    }
    
    public CustomInventory setItem(int slot, Button item, Consumer<InventoryClickEvent> event) {
        ItemStack itemStack = item.getItem();
        ItemMeta meta = itemStack.getItemMeta();
        
        if (meta != null) {
            String id = "event-" + getEvents().size();
            utils.NBTUtils.set(meta, "sutils_id", id);
            meta.setLore(item.getCompleteLore());
            itemStack.setItemMeta(meta);
            getEvents().put(id, event);
        }
    
        getInventory().setItem(slot, itemStack);
        return this;
    }
    
    public CustomInventory setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> event) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String id = "event-" + getEvents().size();
            utils.NBTUtils.set(meta, "sutils_id", id);
            item.setItemMeta(meta);
            getEvents().put(id, event);
        }
        
        getInventory().setItem(slot, item);
        return this;
    }
    
    public CustomInventory setItem(int slot, ItemStack item) {
        return setItem(slot, item, (e) -> e.setCancelled(true));
    }
    
    public CustomInventory fill(ItemStack item) {
        for (int i = 0; i<getSize(); i++) setItem(i, item);
        return this;
    }
    
    public CustomInventory border(ItemStack item) {
        for (int i = 0; i<9; i++) setItem(i, item);
        for (int line = 0; line < size / 9D; line++) {
            setItem(line * 9, item);
            setItem((int) ((line * 9D) + 8), item);
        }
        for (int i = (size - 10); i<(size - 1); i++) setItem(i, item);
        return this;
    }
    
    public static Optional<CustomInventory> getInventory(SantioUtils utils, Player player) {
        return utils.getInventories().values()
                .stream()
                .filter((CustomInventory i) -> i.isOpen(player))
                .findFirst();
    }
    
}
