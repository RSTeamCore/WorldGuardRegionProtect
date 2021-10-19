package net.ritasister.rslibs.api;

import org.bukkit.ChatColor;

public class Color {

    public static String getColorCode(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
