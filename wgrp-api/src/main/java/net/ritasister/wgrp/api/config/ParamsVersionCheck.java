package net.ritasister.wgrp.api.config;

import org.jetbrains.annotations.NotNull;

/**
 * Represent class for a checking version of configs.
 * @param <T>
 */
public interface ParamsVersionCheck<C, T> {

    /**
     * Getting dateFormat in string value.
     * @return date format what you chose.
     */
    String getSimpleDateFormat();

    /**
     * Actually get a version of config.
     */
    String getCurrentVersion(C configType, final @NotNull T currentYaml);

    /**
     * Get a new version of config from jar.
     */
    String getNewVersion(C configType, final @NotNull T yamlConfiguration);

    /**
     *
     * @param version
     * @return
     */
    boolean checkMatches(@NotNull String version);

}
