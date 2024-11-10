package net.ritasister.wgrp.api.implementation;

import net.ritasister.wgrp.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.api.regions.RegionAction;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ApiRegionAction implements RegionAction {

    private final WorldGuardRegionProtectPlugin plugin;

    public ApiRegionAction(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NonNull Type getType() {
        return plugin.getRegionAction().getType();
    }

}
