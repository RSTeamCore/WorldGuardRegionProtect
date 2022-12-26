package net.rsteamcore.wgrp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.rsteamcore.wgrp.rslibs.api.WorldGuardRegionProtectApi;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WGRPBukkitPlugin extends JavaPlugin {

    private WorldGuardRegionProtect wgRegionProtect;

    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(new WGRPModule(this));
        wgRegionProtect = injector.getInstance(WorldGuardRegionProtect.class);
        wgRegionProtect.load();
        WorldGuardRegionProtectApi worldGuardRegionProtectApi = new WorldGuardRegionProtectApi();
    }

    @Override
    public void onDisable() {
        try{
            this.wgRegionProtect.unload();
        } catch (NullPointerException exception) {
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            System.out.println("wgRegionProtect is null, we are disabling plugin!");
        }
    }
}
