package net.ritasister.wgrp;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class WGRPModule extends AbstractModule {

    private final WorldGuardRegionProtect wgRegionProtect;

    public WGRPModule(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect=wgRegionProtect;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        bind(WorldGuardRegionProtect.class).toInstance(wgRegionProtect);
    }
}
