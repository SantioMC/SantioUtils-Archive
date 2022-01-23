package me.santio.utils;

import lombok.Getter;
import me.santio.utils.commands.Command;
import me.santio.utils.inventories.CustomInventory;
import me.santio.utils.listeners.InventoryListener;
import me.santio.utils.listeners.QueryListener;
import me.santio.utils.queries.QueryUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

@SuppressWarnings("unused")
public class SantioUtils {

    @Getter private final JavaPlugin plugin;
    @Getter private final HashMap<String, CustomInventory> inventories = new HashMap<>();
    
    public NBTUtils NBTUtils;
    public QueryUtils queryUtils;
    
    public SantioUtils(JavaPlugin plugin) {
        this.plugin = plugin;
        
        this.NBTUtils = new NBTUtils(this);
        this.queryUtils = new QueryUtils(this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(this), getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new QueryListener(), getPlugin());
    }
    
    public final CustomInventory createInventory(int size, String name) {
        return new CustomInventory(size, name, this);
    }
    
    @SafeVarargs
    public final void registerCommand(Class<? extends Command>... command) {
        // TODO: Make it register
    }
    
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
