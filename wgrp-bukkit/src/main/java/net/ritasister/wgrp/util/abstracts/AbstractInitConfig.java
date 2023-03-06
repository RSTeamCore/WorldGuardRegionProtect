package net.ritasister.wgrp.util.abstracts;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.util.interfaces.InitConfigImpl;

public abstract class AbstractInitConfig<T> implements InitConfigImpl<T> {

    @Override
    public void initConfig(final T clazz, final WGRPBukkitPlugin wgrpBukkitPlugin) {}

}
