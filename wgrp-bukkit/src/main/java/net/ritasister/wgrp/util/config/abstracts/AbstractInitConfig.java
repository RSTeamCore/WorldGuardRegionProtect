package net.ritasister.wgrp.util.config.abstracts;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.util.config.interfaces.InitConfig;

public abstract class AbstractInitConfig<T> implements InitConfig<T> {

    @Override
    public void initConfig(final T clazz, final WGRPBukkitPlugin wgrpBukkitPlugin) {}

}
