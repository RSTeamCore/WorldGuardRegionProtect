package net.ritasister.wgrp.api.implementation;

import net.ritasister.wgrp.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.api.manager.tools.ToolsAdapterManager;

public class ApiToolsProtect<P> implements ToolsAdapterManager<P> {

    private final WorldGuardRegionProtectPlugin plugin;

    public ApiToolsProtect(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isSuperPickaxeActive(final P player) {
        return plugin.getApiProvider().getToolsAdapterManager().isSuperPickaxeActive(player);
    }

}
