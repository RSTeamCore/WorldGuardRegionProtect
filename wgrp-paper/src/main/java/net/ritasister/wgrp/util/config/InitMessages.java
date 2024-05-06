package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.core.api.config.Container;
import org.jetbrains.annotations.NotNull;

public interface InitMessages<T> {

    Container initMessages(@NotNull T wgrpBukkitPlugin, Config config);

}
