package me.santio.utils;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.WordUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public final class ChatUtils {
    private static final Pattern hexPattern = Pattern.compile("&#[a-fA-F0-9]{6}");
    
    public static String tacc(String text) {
        if(VersionUtils.getMajor() >= 16) {
            Matcher match = hexPattern.matcher(text);
            while (match.find()) {
                String color = text.substring(match.start(), match.end());
                text = text.replace(color, ChatColor.of(color.substring(1)).toString());
                match = hexPattern.matcher(text);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    
    public static String strip(String text) {
        return ChatColor.stripColor(text);
    }
    
    public static String toID(String text) {
        return strip(tacc(text)).replaceAll(" ", "_").toLowerCase();
    }
    
    public static String[] loreWrap(String description) {
        return WordUtils.wrap(description, 40).split("\n");
    }
}
