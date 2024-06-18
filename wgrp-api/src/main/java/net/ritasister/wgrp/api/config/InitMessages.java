package net.ritasister.wgrp.api.config;

import org.jetbrains.annotations.NotNull;

public interface InitMessages<T, C, M> {

    M initMessages(@NotNull T plugin, C config);

}
