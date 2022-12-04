package net.ritasister.wgrp;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import lombok.SneakyThrows;
import net.ritasister.wgrp.rslibs.api.CommandWE;
import net.ritasister.wgrp.rslibs.api.interfaces.ICommandWE;
import net.ritasister.wgrp.rslibs.util.wg.Iwg;
import net.ritasister.wgrp.util.wg.wg7;

public class WGRPModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(Iwg.class, wg7.class).build(IwgFactory.class));
        install(new FactoryModuleBuilder().implement(ICommandWE.class, CommandWE.class).build(Iwg.class));
        install(new FactoryModuleBuilder().implement(ILoadLibs.class, LoadLibs.class).build(ILoadLibsFactory.class));
        bind(IWGRPBukkit.class).to(WorldGuardRegionProtect.class);
    }
}