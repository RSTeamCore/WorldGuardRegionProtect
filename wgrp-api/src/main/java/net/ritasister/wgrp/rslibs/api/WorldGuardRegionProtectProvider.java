package net.ritasister.wgrp.rslibs.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class WorldGuardRegionProtectProvider {

    private static WorldGuardRegionProtect instance;

    private WorldGuardRegionProtectProvider() {
        throw new IllegalArgumentException();
    }

    @ApiStatus.Internal
    public static void setWorldGuardRegionProtect(final WorldGuardRegionProtect instance) {
        if (WorldGuardRegionProtectProvider.instance != null) {
            throw new IllegalArgumentException("WorldGuardRegionProtectProvider is already set!");
        }
        WorldGuardRegionProtectProvider.instance = instance;
    }

    public static @NotNull WorldGuardRegionProtect get() {
        return instance;
    }

}
