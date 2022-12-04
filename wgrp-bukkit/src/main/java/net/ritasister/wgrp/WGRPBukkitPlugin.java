package net.ritasister.wgrp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public final class WGRPBukkitPlugin extends JavaPlugin {

    @Inject
    private WorldGuardRegionProtect wgRegionProtect;

    @SneakyThrows
    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(new WGRPModule());
        injector.getInstance(IWGRPBukkit.class);
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