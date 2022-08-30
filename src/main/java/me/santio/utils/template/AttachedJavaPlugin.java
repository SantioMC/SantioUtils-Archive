package me.santio.utils.template;

import lombok.Getter;
import me.santio.utils.SantioUtils;
import me.santio.utils.bukkit.AsyncUtils;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AttachedJavaPlugin extends JavaPlugin {
    
    @Getter
    private static SantioUtils utils;
    
    @Getter
    private static AsyncUtils scheduler;
    
    @Override
    public void onLoad() {
        utils = new SantioUtils(this);
        scheduler = new AsyncUtils(this);
    }

}
