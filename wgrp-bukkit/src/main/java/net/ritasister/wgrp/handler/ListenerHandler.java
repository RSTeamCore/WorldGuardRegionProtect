package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.listener.RegionProtect;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class ListenerHandler extends AbstractHandler<PluginManager> {

    private final WorldGuardRegionProtect wgRegionProtect;

    public ListenerHandler(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect = wgRegionProtect;
    }

    @Override
    public void handle(final @NotNull PluginManager pluginManager) {
        final RegionProtect regionProtect = new RegionProtect(wgRegionProtect);
        pluginManager.registerEvents(regionProtect, wgRegionProtect.getWGRPBukkitPlugin());

        wgRegionProtect.getLogger().info("All listeners registered successfully!");
    }

}
