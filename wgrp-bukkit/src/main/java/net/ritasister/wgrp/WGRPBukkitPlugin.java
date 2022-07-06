package net.ritasister.wgrp;

import org.bukkit.plugin.java.JavaPlugin;

public final class WGRPBukkitPlugin extends JavaPlugin {

    private WorldGuardRegionProtect wgRegionProtect;

    @Override
    public void onEnable() {
        this.wgRegionProtect = new WorldGuardRegionProtect(this);
        getWgRegionProtect().load();
    }

    @Override
    public void onDisable() {
        this.wgRegionProtect.unload();
    }

    private WorldGuardRegionProtect getWgRegionProtect() {
        return this.wgRegionProtect;
    }
}