package net.ritasister.wgrp.plugin;

import net.ritasister.wgrp.api.logging.PluginLogger;
import net.ritasister.wgrp.api.platform.Platform;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface WorldGuardRegionProtectBootstrap {

    /**
     * Gets the plugin logger
     *
     * @return the logger
     */
    PluginLogger getPluginLogger();

    /**
     * Gets a string of the plugin's version
     *
     * @return the version of the plugin
     */
    String getVersion();

    /**
     * Gets the time when the plugin first started in millis.
     *
     * @return the enable time
     */
    Instant getStartupTime();

    /**
     * Gets the platform type this instance of LuckPerms is running on.
     *
     * @return the platform type
     */
    Platform.Type getType();

    /**
     * Gets the name or "brand" of the running platform
     *
     * @return the server brand
     */
    String getServerBrand();

    /**
     * Gets the version of the running platform
     *
     * @return the server version
     */
    String getServerVersion();

    /**
     * Gets the Minecraft version this server is running
     *
     * @return the Minecraft version
     */
    String getMinecraftVersion();

    /**
     * Gets the name associated with this server
     *
     * @return the server name
     */
    default @Nullable String getServerName() {
        return null;
    }

    /**
     * Gets a player by their unique ID
     *
     * @param uniqueId the unique ID
     * @return the player, if they are online
     */
    Optional<?> getPlayer(UUID uniqueId);
}
