package net.ritasister.rslibs.api;

import org.bukkit.ChatColor;

public class Color {

    /**
     * Get colour for any message.
     *
     * @return message
     */
    public static String getColorCode(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
