package me.santio.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

@SuppressWarnings("unused")
public final class NBTUtils {

    public SantioUtils utils;
    public NBTUtils(SantioUtils utils) {
        this.utils = utils;
    }
    
    private NamespacedKey getKey(String key) {
        return new NamespacedKey(utils.getPlugin(), key);
    }
    
    public void set(PersistentDataHolder holder, String key, String value) {
        holder.getPersistentDataContainer().set(getKey(key), PersistentDataType.STRING, value);
    }
    
    public String get(PersistentDataHolder holder, String key) {
        return holder.getPersistentDataContainer().get(getKey(key), PersistentDataType.STRING);
    }

}
