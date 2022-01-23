package me.santio.utils.inventories.buttons;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.santio.utils.ChatUtils;
import me.santio.utils.CustomItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Accessors(chain = true)
public abstract class Button {

    @Setter @Getter private String description = null;
    @Getter private final CustomItem item;
    
    public abstract void onClick(InventoryClickEvent event);
    public abstract List<String> getLore();
    
    public List<String> getCompleteLore() {
        List<String> lore = new ArrayList<>();
        if (description != null) {
            lore.add("§f");
            lore.addAll(Arrays.stream(
                    ChatUtils.loreWrap(ChatColor.GRAY + description))
                    .map(l -> ChatColor.GRAY + l)
                    .collect(Collectors.toList()));
        }
        lore.addAll(getLore());
        return lore;
    }
    
    public Button(CustomItem original) {
        original.setItemLore(Collections.singletonList("§cLoading..."));
        this.item = original;
    }
    
}
