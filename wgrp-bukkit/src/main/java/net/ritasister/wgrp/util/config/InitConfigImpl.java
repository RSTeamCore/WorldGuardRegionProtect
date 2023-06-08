package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.util.config.interfaces.InitConfig;

public class InitConfigImpl<T> implements InitConfig<T> {

    @Override
    public void initConfig(final T clazz, final WGRPBukkitPlugin wgrpBukkitPlugin) {}

}
