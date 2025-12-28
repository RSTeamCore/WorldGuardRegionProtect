package net.ritasister.wgrp.api.implementation;

import net.ritasister.wgrp.plugin.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionProtectMetadata;
import net.ritasister.wgrp.api.platform.Platform;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class ApiPlatform implements Platform, WorldGuardRegionProtectMetadata {

    private final WorldGuardRegionProtectPlugin plugin;

    public ApiPlatform(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getVersion() {
        return this.plugin.getBootstrap().getVersion();
    }

    @Override
    public @NotNull String getApiVersion() {
        final String[] parts = this.plugin.getBootstrap().getVersion().split("\\.");
        return parts.length >= 2 ? parts[0] + '.' + parts[1] : this.plugin.getBootstrap().getVersion();
    }

    @Override
    public @NotNull Instant getStartTime() {
        return plugin.getBootstrap().getStartupTime();
    }

    @Override
    public Platform.@NotNull Type getType() {
        return this.plugin.getBootstrap().getType();
    }

}
