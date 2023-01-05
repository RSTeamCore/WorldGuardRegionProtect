package net.ritasister.wgrp;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import lombok.AllArgsConstructor;
import net.ritasister.wgrp.rslibs.interfaces.LoadLibs;
import net.ritasister.wgrp.rslibs.interfaces.LoadLibsFactory;

@AllArgsConstructor
public class WGRPModule extends AbstractModule {

    private final WGRPBukkitPlugin wgrpBukkitPlugin;

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(LoadLibs.class, LoadLibsImpl.class).build(LoadLibsFactory.class));
        bind(WGRPBukkitPlugin.class).toInstance(wgrpBukkitPlugin);
    }
}
