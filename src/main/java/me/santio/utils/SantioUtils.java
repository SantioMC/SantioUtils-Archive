package me.santio.utils;

import lombok.Getter;
import me.santio.utils.commands.Command;
import me.santio.utils.inventories.CustomInventory;
import me.santio.utils.listeners.InventoryListener;
import me.santio.utils.listeners.QueryListener;
import me.santio.utils.queries.QueryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class SantioUtils {

    @Getter private final JavaPlugin plugin;
    @Getter private final HashMap<String, CustomInventory> inventories = new HashMap<>();
    @Getter private final HashMap<Method, Class<?>> supportReloads = new HashMap<>();
    
    private final List<Class<?>> supportedReloads = Arrays.asList(PlayerJoinEvent.class, PlayerQuitEvent.class);
    
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
    
    public final void supportReloads() {
        try {
            for (RegisteredListener listener : HandlerList.getRegisteredListeners(getPlugin())) {
                for (Method method : listener.getListener().getClass().getDeclaredMethods()) {
                    if (method.getAnnotation(EventHandler.class) == null || method.getAnnotation(SupportReloads.class) == null)
                        continue;
                    if (!method.isAccessible()) method.setAccessible(true);
                    List<Class<?>> parameters = Arrays.asList(method.getParameterTypes());
                    switch (parameters.get(0).getSimpleName()) {
                        case "PlayerJoinEvent":
                            for (Player player : Bukkit.getOnlinePlayers()) method.invoke(listener.getListener(), new PlayerJoinEvent(player, ""));
                            break;
                        case "PlayerQuitEvent":
                            for (Player player : Bukkit.getOnlinePlayers()) method.invoke(listener.getListener(), new PlayerQuitEvent(player, ""));
                            break;
                        default:
                            getPlugin().getLogger().severe("[SantioUtils] Failed to invoke "+parameters.get(0).getName()+"!");
                    }
                }
            }
        } catch(IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
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
