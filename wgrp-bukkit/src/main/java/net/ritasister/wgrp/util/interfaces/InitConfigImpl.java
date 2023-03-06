package net.ritasister.wgrp.util.interfaces;

import net.ritasister.wgrp.WGRPBukkitPlugin;

public interface InitConfigImpl<T> {

    void initConfig(T clazz, WGRPBukkitPlugin wgrpBukkitPlugin);

}
