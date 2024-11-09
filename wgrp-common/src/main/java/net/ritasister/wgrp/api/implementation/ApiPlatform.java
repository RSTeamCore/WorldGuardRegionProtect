package net.ritasister.wgrp.api.implementation;

import net.ritasister.wgrp.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionMetadata;
import net.ritasister.wgrp.api.platform.Platform;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ApiPlatform implements Platform, WorldGuardRegionMetadata {

    private final WorldGuardRegionProtectPlugin plugin;

    public ApiPlatform(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NonNull String getVersion() {
        return this.plugin.getWorldGuardMetadata().getVersion();
    }

    @Override
    public @NonNull String getApiVersion() {
        String[] version = this.plugin.getWorldGuardMetadata().getVersion().split("\\.");
        return version[0] + '.' + version[1];
    }

    @Override
    public Platform.@NonNull Type getType() {
        return this.plugin.getType();
    }

}
