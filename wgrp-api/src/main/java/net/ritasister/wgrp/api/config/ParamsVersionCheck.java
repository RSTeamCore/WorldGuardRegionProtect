package net.ritasister.wgrp.api.config;

import org.jetbrains.annotations.NotNull;

/**
 * Represent class for a checking version of configs.
 * @param <T>
 */
public interface ParamsVersionCheck<T> {

    /**
     * Getting dateFormat in string value.
     * @return date format what you chose.
     */
    String getSimpleDateFormat();

    /**
     * Actually get a version of config.
     */
    String getCurrentVersion(String configType, final @NotNull T currentYaml);

    /**
     * Get a new version of config from jar.
     */
    String getNewVersion(String configType, final @NotNull T yamlConfiguration);

    /**
     *
     * @param version
     * @return
     */
    boolean checkMatches(@NotNull String version);

}
