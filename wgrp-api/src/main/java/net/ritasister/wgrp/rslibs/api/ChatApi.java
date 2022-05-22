package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.rslibs.api.interfaces.IChatApi;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class ChatApi implements IChatApi {

    /**
     * Get colour for any message.
     *
     * @return message
     */
    public String getColorCode(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Get \\n space from config for sender message without color.
     *
     * @return message
     */
    public String getDoubleTabSpaceWithoutColor(@NotNull String message) {
        return message.replace("\\n", "\n");
    }

    /**
     * Get \\n space from config for sender message with color.
     *
     * @return message
     */
    public String getDoubleTabSpaceWithColor(@NotNull String message) {
        return getColorCode(message.replace("\\n", "\n"));
    }
}
