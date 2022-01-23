package me.santio.utils.inventories.buttons;

import lombok.Getter;
import me.santio.utils.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ToggleButton extends Button {
    
    @Getter private final Consumer<Boolean> updated;
    @Getter private boolean state;
    
    public ToggleButton(CustomItem item, boolean current, Consumer<Boolean> updated) {
        super(item);
        state = current;
        this.updated = updated;
    }
    
    @Override
    public void onClick(InventoryClickEvent event) {
        state = !state;
        this.updated.accept(state);
        Bukkit.broadcastMessage("State: "+state);
    }
    
    @Override
    public List<String> getLore() {
        return Arrays.asList(
                        "§f",
                (state ? "§b" : "§7") + "Enabled",
                (!state ? "§b" : "§7") + "Disabled",
                "§f"
        );
    }
    
    
}
