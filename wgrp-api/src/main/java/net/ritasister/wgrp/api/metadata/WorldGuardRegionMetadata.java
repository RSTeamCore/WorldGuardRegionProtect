package net.ritasister.wgrp.api.metadata;

/**
 * <p>Provides information about the WorldGuardRegionProtect plugin.
 * </p>
 */
public interface WorldGuardRegionMetadata {

    /**
     * Gets the plugin version
     *
     * @return the <code>version</code> of the plugin running on the platform
     */
    String getVersion();

    /**
     * Gets the API version
     *
     * @return the <code>version</code> of the API running on the platform
     */
    String getApiVersion();

}
