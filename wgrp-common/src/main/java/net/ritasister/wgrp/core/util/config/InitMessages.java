package net.ritasister.wgrp.core.util.config;

import net.rsteamcore.config.Container;
import org.jetbrains.annotations.NotNull;

public interface InitMessages<T> {

    Container initMessages(@NotNull T wgrpBukkitPlugin, Config config);

}
