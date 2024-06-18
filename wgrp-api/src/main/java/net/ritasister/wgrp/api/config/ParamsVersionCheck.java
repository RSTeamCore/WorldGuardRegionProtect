package net.ritasister.wgrp.api.config;

import org.jetbrains.annotations.NotNull;

public interface ParamsVersionCheck<CT, T> {

    String getSimpleDateFormat();

    String getCurrentVersion(CT configType, final @NotNull T currentYaml);

    String getNewVersion(CT configType, final @NotNull T yamlConfiguration);

}
