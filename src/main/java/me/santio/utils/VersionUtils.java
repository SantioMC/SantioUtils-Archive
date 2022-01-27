package me.santio.utils;

import org.bukkit.Bukkit;

public class VersionUtils {
    
    // Version format:
    // 1  . 16       .  5
    // 1  . <major>  .  <minor>
    // [0], [1]      ,  [2]
    
    public static String getVersion() {
        return Bukkit.getVersion();
    }
    
    public static int getMajor() {
        return Integer.parseInt(getVersion().split("\\.")[1]);
    }
    
    public static int getMinor() {
        return Integer.parseInt(getVersion().split("\\.")[2]);
    }
    
}
