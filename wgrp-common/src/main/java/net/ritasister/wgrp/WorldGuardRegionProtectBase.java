package net.ritasister.wgrp;

import net.ritasister.wgrp.api.WorldGuardRegionProtect;

/**
 * Only need for get api of this plugin.
 */
@FunctionalInterface
public interface WorldGuardRegionProtectBase {

    /**
     * This method is used internally when getting the api instance.
     *
     * @return {@link WorldGuardRegionProtect} instance
     */
    WorldGuardRegionProtect getApi();

}
