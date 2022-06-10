package net.ritasister.wgrp;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

public final class WGRPBukkitPlugin extends JavaPlugin {

    @Inject
    private WorldGuardRegionProtect wgRegionProtect;

    public Injector injector;

    @Override
    public void onEnable() {
        wgRegionProtect = new WorldGuardRegionProtect();
        injector = Guice.createInjector(new WGRPModule(wgRegionProtect, this));
        this.wgRegionProtect.load();
    }

    @Override
    public void onDisable() {
        wgRegionProtect.getUtilConfig().getConfig().saveConfig();
    }
}