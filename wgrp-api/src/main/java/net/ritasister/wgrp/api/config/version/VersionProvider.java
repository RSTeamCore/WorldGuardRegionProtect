package net.ritasister.wgrp.api.config.version;

/**
 * Interface for providing server version information.
 */
@FunctionalInterface
public interface VersionProvider {

    /**
     * Retrieves the current server version.
     *
     * @return The server version as a string.
     */
    String getServerVersion();
}

