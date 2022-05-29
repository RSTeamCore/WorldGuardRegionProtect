package net.ritasister.wgrp;

import org.bukkit.plugin.java.JavaPlugin;

public final class WGRPBukkitPlugin extends JavaPlugin {

    private WorldGuardRegionProtect wgRegionProtect;

    @Override
    public void onEnable() {
        wgRegionProtect = new WorldGuardRegionProtect(this);
        this.getWgRegionProtect().load();
    }

    @Override
    public void onDisable() {
        getWgRegionProtect().getUtilConfig().getConfig().saveConfig();
    }

    private WorldGuardRegionProtect getWgRegionProtect() {
        return this.wgRegionProtect;
    }
}