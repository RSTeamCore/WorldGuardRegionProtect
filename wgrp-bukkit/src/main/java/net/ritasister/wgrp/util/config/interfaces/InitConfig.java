package net.ritasister.wgrp.util.config.interfaces;

import net.ritasister.wgrp.WGRPBukkitPlugin;

public interface InitConfig<T> {

    void initConfig(T clazz, WGRPBukkitPlugin wgrpBukkitPlugin);

}
