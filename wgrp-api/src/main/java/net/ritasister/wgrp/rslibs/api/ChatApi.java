package net.ritasister.wgrp.rslibs.api;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class ChatApi {

    /**
     * Get colour for any message.
     *
     * @return message
     */
    public String getColorCode(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
