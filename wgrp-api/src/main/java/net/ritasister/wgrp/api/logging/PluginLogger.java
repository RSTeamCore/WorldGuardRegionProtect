package net.ritasister.wgrp.api.logging;

/**
 * Represents the logger instance being used by WorldGuardRegionProtect on the platform.
 *
 * <p>Messages sent using the logger are sent prefixed with the WorldGuardRegionProtect tag,
 * and on some implementations will be colored depending on the message type.</p>
 */
public interface PluginLogger {

    void info(String message);

    void warn(String message);

    void warn(String message, Throwable throwable);

    void severe(String message);

    void severe(String message, Throwable throwable);

}
