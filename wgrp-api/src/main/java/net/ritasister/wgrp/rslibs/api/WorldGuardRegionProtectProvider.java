package net.ritasister.wgrp.rslibs.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public class WorldGuardRegionProtectProvider {

    private static WorldGuardRegionProtect worldGuardRegionProtect;

    private WorldGuardRegionProtectProvider() {
        throw new IllegalArgumentException();
    }

    @ApiStatus.Internal
    public static void setWorldGuardRegionProtect(final WorldGuardRegionProtect worldGuardRegionProtect) {
        if (WorldGuardRegionProtectProvider.worldGuardRegionProtect != null) {
            throw new IllegalArgumentException("WorldGuardRegionProtectProvider is already set!");
        }
        WorldGuardRegionProtectProvider.worldGuardRegionProtect = worldGuardRegionProtect;
    }

    public static @Nullable WorldGuardRegionProtect get() {
        return worldGuardRegionProtect;
    }

}
