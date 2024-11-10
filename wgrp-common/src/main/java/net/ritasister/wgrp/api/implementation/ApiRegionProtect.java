package net.ritasister.wgrp.api.implementation;

import net.ritasister.wgrp.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.rslibs.exceptions.NoSelectionException;

import java.util.List;
import java.util.Map;

public class ApiRegionProtect<L, P, R> implements RegionAdapterManager<L, P, R> {

    private final WorldGuardRegionProtectPlugin plugin;

    public ApiRegionProtect(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;
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

    @Override
    public String getProtectRegionNameByIntersection(final R selection) throws NoSelectionException {
        return plugin.getRegionAdapter().getProtectRegionNameByIntersection(selection);
    }

}
