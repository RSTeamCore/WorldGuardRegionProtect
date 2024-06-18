package net.ritasister.wgrp.api.config;

import org.jetbrains.annotations.NotNull;

/**
 * Represent class for a checking version of configs.
 * @param <CT>
 * @param <T>
 */
public interface ParamsVersionCheck<CT, T> {

    /**
     * Getting dateFormat in string value.
     * @return date format what you chose.
     */
    String getSimpleDateFormat();

    /**
     * Actually get a version of config.
     */
    String getCurrentVersion(CT configType, final @NotNull T currentYaml);

    /**
     * Get a new version of config from jar.
     */
    String getNewVersion(CT configType, final @NotNull T yamlConfiguration);

}
