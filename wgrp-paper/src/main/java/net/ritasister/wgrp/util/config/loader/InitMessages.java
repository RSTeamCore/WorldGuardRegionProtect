package net.ritasister.wgrp.util.config.loader;

import org.jetbrains.annotations.NotNull;

public interface InitMessages<T, C, M> {

    M initMessages(@NotNull T plugin, C config);

}
