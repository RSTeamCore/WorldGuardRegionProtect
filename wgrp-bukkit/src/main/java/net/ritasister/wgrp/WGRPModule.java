package net.ritasister.wgrp;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class WGRPModule extends AbstractModule {

    private final WorldGuardRegionProtect wgRegionProtect;
    private final WGRPBukkitPlugin wgrpBukkitPlugin;

    public WGRPModule(WorldGuardRegionProtect wgRegionProtect, WGRPBukkitPlugin wgrpBukkitPlugin) {
        this.wgRegionProtect=wgRegionProtect;
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        bind(WorldGuardRegionProtect.class).toInstance(wgRegionProtect);
        bind(WGRPBukkitPlugin.class).toInstance(wgrpBukkitPlugin);
    }
}
