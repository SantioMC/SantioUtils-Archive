package me.santio.utils.listeners;

import me.santio.utils.SantioUtils;
import me.santio.utils.inventories.CustomInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Consumer;

import java.util.Optional;

public class InventoryListener implements Listener {
    
    private final SantioUtils utils;
    public InventoryListener(SantioUtils utils) {
        this.utils = utils;
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Optional<CustomInventory> inventory = utils.getInventories().values()
                .stream()
                .filter((CustomInventory i) -> i.isOpen((Player) event.getPlayer()))
                .findFirst();
        
        event.getPlayer().removeMetadata("sutils_inventory_open", utils.getPlugin());
        inventory.ifPresent((i) -> {
            if (i.isDeleteOnClose()) i.delete();
        });
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || !(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        
        // Find which inventory the player has open
        Optional<CustomInventory> inventory = utils.getInventories().values()
                .stream()
                .filter((CustomInventory i) -> i.isOpen(player))
                .findFirst();
    
        if (!inventory.isPresent()) return;
        
        // Get item
        ItemStack item = event.getClickedInventory().getItem(event.getSlot());
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        
        // Fetch event ID
        Consumer<InventoryClickEvent> itemEvent = inventory.get().getEvents().get(utils.NBTUtils.get(meta, "sutils_id"));
        if (itemEvent == null) return;
    
        // Run event
        itemEvent.accept(event);
    }
    
}
