package net.ritasister.wgrp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class for WorldGuardRegionProtect as a Bukkit plugin.
 */
public final class WGRPBukkitPlugin extends JavaPlugin {

    private WorldGuardRegionProtect wgRegionProtect;

    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(new WGRPModule(this));
        wgRegionProtect = injector.getInstance(WorldGuardRegionProtect.class);
        wgRegionProtect.load();
    }

    @Override
    public void onDisable() {
        try {
            this.wgRegionProtect.unLoad();
        } catch (NullPointerException exception) {
            System.out.println("wgRegionProtect is null, we are disabling plugin!");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }
    }

}
