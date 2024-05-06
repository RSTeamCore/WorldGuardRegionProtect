package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.rslibs.api.config.Container;
import org.jetbrains.annotations.NotNull;

public interface InitMessages<T> {

    Container initMessages(@NotNull T plugin, Config config);

}
