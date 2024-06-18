package net.ritasister.wgrp.api.logging;

/**
 * Represents the logger instance being used by WorldGuardRegionProtect on the platform.
 *
 * <p>Messages sent using the logger are sent prefixed with the WorldGuardRegionProtect tag,
 * and on some implementations will be colored depending on the message type.</p>
 */
public interface PluginLogger {

    /**
     * Through info while running plugin.
     */
    void info(String message);

    /**
     * Through warning while running plugin.
     */
    void warn(String message);

    /**
     * Through throwable warning while running plugin.
     */
    void warn(String message, Throwable throwable);

    /**
     * Through severe error while running plugin.
     */
    void severe(String message);

    /**
     * Through throwable severe error while running plugin.
     */
    void severe(String message, Throwable throwable);

}
