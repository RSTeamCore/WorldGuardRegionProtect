package net.ritasister.wgrp.api.implementation;

import net.ritasister.wgrp.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ApiRegionProtect<L, P> implements RegionAdapterManager<L, P> {

    private final WorldGuardRegionProtectPlugin plugin;

    public ApiRegionProtect(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isOwnerRegion(@NotNull final L location, @NotNull final Map<String, List<String>> regions, final UUID uniqueId) {
        return plugin.getRegionAdapter().isOwnerRegion(location, regions, uniqueId);
    }

    @Override
    public boolean isOwnerRegion(@NotNull final L location, final UUID uniqueId) {
        return plugin.getRegionAdapter().isOwnerRegion(location, uniqueId);
    }

    @Override
    public boolean isMemberRegion(@NotNull final L location, @NotNull final Map<String, List<String>> regions, final UUID uniqueId) {
        return plugin.getRegionAdapter().isMemberRegion(location, regions, uniqueId);
    }

    @Override
    public boolean isMemberRegion(@NotNull final L location, final UUID uniqueId) {
        return plugin.getRegionAdapter().isMemberRegion(location, uniqueId);
    }

    @Override
    public boolean isPriorityRegion(@NotNull final L location) {
        return plugin.getRegionAdapter().isPriorityRegion(location);
    }

    @Override
    public boolean checkStandingRegion(final L location, final Map<String, List<String>> regions) {
        return plugin.getRegionAdapter().checkStandingRegion(location, regions);
    }

    @Override
    public boolean checkStandingRegion(final L location) {
        return plugin.getRegionAdapter().checkStandingRegion(location);
    }

    @Override
    public String getProtectRegionName(final L location) {
        return plugin.getRegionAdapter().getProtectRegionName(location);
    }

    @Override
    public String getProtectRegionNameBySelection(final P player) {
        return plugin.getRegionAdapter().getProtectRegionNameBySelection(player);
    }

}
