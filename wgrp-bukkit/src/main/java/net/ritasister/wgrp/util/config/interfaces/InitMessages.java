package net.ritasister.wgrp.util.config.interfaces;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.util.config.Config;

import net.rsteamcore.config.Container;
import org.jetbrains.annotations.NotNull;

public interface InitMessages {

    Container initMessages(@NotNull WGRPBukkitPlugin wgrpBukkitPlugin, Config config);

}
