package me.santio.utils.inventories.buttons;

import lombok.Getter;
import me.santio.utils.CustomItem;
import me.santio.utils.SantioUtils;
import me.santio.utils.inventories.CustomInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class NumberButton extends Button {
    
    @Getter private final Consumer<Integer> updated;
    @Getter private final SantioUtils utils;
    @Getter private int state;
    
    public NumberButton(SantioUtils utils, CustomItem item, int current, Consumer<Integer> updated) {
        super(item);
        this.utils = utils;
        state = current;
        this.updated = updated;
    }
    
    @Override
    public void onClick(InventoryClickEvent event) {
        Optional<CustomInventory> inventory = CustomInventory.getInventory(utils, (Player) event.getWhoClicked());
        if (!inventory.isPresent()) return;
    
        boolean delete = inventory.get().isDeleteOnClose();
        inventory.get().setDeleteOnClose(false);
        event.getWhoClicked().closeInventory();
        event.getWhoClicked().sendMessage("§7Type in chat the new value for this option!");
        utils.queryUtils.awaitMessage((Player) event.getWhoClicked(), (res) -> Bukkit.getScheduler().runTask(utils.getPlugin(), () -> {
            if (res != null) {
                this.state = Integer.parseInt(res);
                this.updated.accept(state);
            }
            inventory.get().setDeleteOnClose(delete);
            inventory.get().open((Player) event.getWhoClicked());
            inventory.get().updateLore(event.getSlot(), getCompleteLore());
        }), utils::isInteger);
    }
    
    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "§f",
                "§b" + state,
                "§7§oClick to change",
                "§f"
        );
    }
    
    
}
