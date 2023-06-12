package net.ritasister.wgrp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class for WorldGuardRegionProtect as a Bukkit plugin.
 */
@Slf4j
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
        if(this.wgRegionProtect == null) {
            log.error("wgRegionProtect is null, we are disabling plugin!");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.wgRegionProtect.unLoad();
    }

}
