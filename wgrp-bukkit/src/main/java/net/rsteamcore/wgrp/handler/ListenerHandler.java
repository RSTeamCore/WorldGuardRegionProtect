package net.rsteamcore.wgrp.handler;

import net.rsteamcore.wgrp.WorldGuardRegionProtect;
import net.rsteamcore.wgrp.listener.protect.RegionProtect;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public record ListenerHandler(WorldGuardRegionProtect wgRegionProtect) {

    public void listenerHandler(@NotNull PluginManager pluginManager) {
        final RegionProtect regionProtect = new RegionProtect(wgRegionProtect);
        pluginManager.registerEvents(regionProtect, wgRegionProtect.getWGRPBukkitPlugin());

        wgRegionProtect.getLogger().info("All listeners registered successfully!");
    }
}
