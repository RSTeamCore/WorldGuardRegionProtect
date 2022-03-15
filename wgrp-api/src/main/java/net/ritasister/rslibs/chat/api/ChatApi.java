package net.ritasister.rslibs.chat.api;

import org.bukkit.ChatColor;

public class ChatApi {

    /**
     * Get colour for any message.
     *
     * @return message
     */
    public static String getColorCode(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    /**
     * Get \\n space from config for sender message without color.
     *
     * @return message
     */
    public static String getDoubleTabSpaceWithoutColor(String message) {
        return message.replace("\\n", "\n");
    }
    /**
     * Get \\n space from config for sender message with color.
     *
     * @return message
     */
    public static String getDoubleTabSpaceWithColor(String message) {
        return getColorCode(message.replace("\\n", "\n"));
    }
}
