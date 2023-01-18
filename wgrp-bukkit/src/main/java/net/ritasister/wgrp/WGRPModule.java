package net.ritasister.wgrp;

import com.google.inject.AbstractModule;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WGRPModule extends AbstractModule {

    private final WGRPBukkitPlugin wgrpBukkitPlugin;

    @Override
    protected void configure() {
        bind(WGRPBukkitPlugin.class).toInstance(wgrpBukkitPlugin);
    }
}
