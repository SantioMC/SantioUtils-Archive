package me.santio.utils.inventories.buttons;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.santio.utils.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused")
@Accessors(chain = true)
public class ListButton extends Button {
    
    private final ArrayList<String> options = new ArrayList<>();
    @Setter @Getter private String value;
    @Getter private final Consumer<String> updated;
    
    public ListButton(CustomItem item, String current, Consumer<String> updated, String... options) {
        super(item);
        setOptions(Arrays.asList(options));
        setValue(current);
        this.updated = updated;
    }
    
    public ListButton(CustomItem item, List<String> options, String current, Consumer<String> updated) {
        super(item);
        setOptions(options);
        setValue(current);
        this.updated = updated;
    }
    
    public void setOptions(List<String> o) {
        options.clear();
        addOptions(o);
        setValue(o.get(0));
    }
    
    public void addOptions(List<String> o) {
        options.addAll(o);
    }
    
    public void addOption(String o) {
        options.add(o);
    }
    
    @Override
    public void onClick(InventoryClickEvent event) {
        int current = options.indexOf(value);
        current++;
        if (options.size() == current) current = 0;
        value = options.get(current);
        updated.accept(value);
    }
    
    @Override
    public List<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        
        lore.add("§f");
        for (String option : options) lore.add((value.equals(option) ? "§b" : "§7") + option);
        lore.add("§f");
        
        return lore;
    }
}
