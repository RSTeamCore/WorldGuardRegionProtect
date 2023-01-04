package net.ritasister.wgrp;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import lombok.AllArgsConstructor;
import net.ritasister.wgrp.rslibs.interfaces.ILoadLibs;
import net.ritasister.wgrp.rslibs.interfaces.ILoadLibsFactory;

@AllArgsConstructor
public class WGRPModule extends AbstractModule {

    private final WGRPBukkitPlugin wgrpBukkitPlugin;

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(ILoadLibs.class, LoadLibs.class).build(ILoadLibsFactory.class));
        bind(WGRPBukkitPlugin.class).toInstance(wgrpBukkitPlugin);
    }
}
