package net.ritasister.wgrp;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

public final class WGRPBukkitPlugin extends JavaPlugin {

    @Inject private WorldGuardRegionProtect wgRegionProtect;

    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(new WGRPModule());
        wgRegionProtect = injector.getInstance(WorldGuardRegionProtect.class);
        wgRegionProtect.load();
    }

    @Override
    public void onDisable() {
        this.wgRegionProtect.unload();
    }
}