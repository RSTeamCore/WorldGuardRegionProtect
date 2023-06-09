package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WGRPBukkitPlugin;

public interface InitConfig<T> {

    void initConfig(T clazz, WGRPBukkitPlugin wgrpBukkitPlugin);

}
