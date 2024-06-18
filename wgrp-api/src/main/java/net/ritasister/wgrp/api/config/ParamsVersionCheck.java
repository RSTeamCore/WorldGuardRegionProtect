package net.ritasister.wgrp.api.config;

import org.jetbrains.annotations.NotNull;

public interface ParamsVersionCheck<T> {

    @NotNull String getSimpleDateFormat();

    @NotNull String getCurrentVersion(final @NotNull T currentYaml);

    @NotNull String getNewVersion(final @NotNull T yamlConfiguration);

}
