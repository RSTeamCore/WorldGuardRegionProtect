package net.ritasister.wgrp.core.api;

import net.ritasister.wgrp.api.RegionAdapter;
import net.ritasister.wgrp.api.WorldGuardRegionMetadata;
import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class WorldGuardRegionProtectProvider implements WorldGuardRegionProtect {

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

    @Override
    public <T, P, R> RegionAdapter<T, P, R> getRegionAdapter() {
        return null;
    }

    @Override
    public WorldGuardRegionMetadata getWorldGuardMetadata() {
        return null;
    }

}
