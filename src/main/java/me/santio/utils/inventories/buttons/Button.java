package me.santio.utils.inventories.buttons;

import lombok.Getter;
import me.santio.utils.CustomItem;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Collections;
import java.util.List;

public abstract class Button {

    @Getter private final CustomItem item;
    
    public abstract void onClick(InventoryClickEvent event);
    public abstract List<String> getLore();
    public Button(CustomItem original) {
        original.setItemLore(Collections.singletonList("§cLoading..."));
        this.item = original;
    }
    
}
