package net.ritasister.wgrp.api;

import lombok.NonNull;

/**
 * Provides information about the WorldGuardRegionProtect plugin.
 */
public interface WorldGuardRegionMetadata {

    /**
     * Gets the plugin version
     *
     * @return the version of the plugin running on the platform
     */
    @NonNull String getVersion();

    /**
     * Gets the API version
     *
     * @return the version of the API running on the platform
     */
    @NonNull String getApiVersion();

}
