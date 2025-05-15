package net.ritasister.wgrp.api.implementation;

import net.ritasister.wgrp.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionProtectMetadata;
import net.ritasister.wgrp.api.platform.Platform;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ApiPlatform implements Platform, WorldGuardRegionProtectMetadata {

    private final WorldGuardRegionProtectPlugin plugin;

    public ApiPlatform(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NonNull String getVersion() {
        return this.plugin.getMetaData().getVersion();
    }

    @Override
    public @NonNull String getApiVersion() {
        final String[] version = this.plugin.getMetaData().getVersion().split("\\.");
        return version[0] + '.' + version[1];
    }

    @Override
    public Platform.@NonNull Type getType() {
        return this.plugin.getType();
    }

}
