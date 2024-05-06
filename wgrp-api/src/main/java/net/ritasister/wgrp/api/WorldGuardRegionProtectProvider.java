package net.ritasister.wgrp.api;

import org.jetbrains.annotations.ApiStatus;

/**
 * Provider to get the WorldGuardRegionProtect api instance.
 */
public final class WorldGuardRegionProtectProvider {

    private static WorldGuardRegionProtect worldGuardRegionProtect;
    private static WorldGuardRegionMetadata metadata;

    @ApiStatus.Internal
    private WorldGuardRegionProtectProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    @ApiStatus.Internal
    public static void setWorldGuardRegionProtect(final WorldGuardRegionProtect worldGuardRegionProtect) {
        if (WorldGuardRegionProtectProvider.worldGuardRegionProtect != null) {
            throw new IllegalArgumentException("MaintenanceProvider is already set!");
        }
        WorldGuardRegionProtectProvider.worldGuardRegionProtect = worldGuardRegionProtect;
    }

}
