package net.ritasister.wgrp.api.config;

import org.jetbrains.annotations.NotNull;

/**
 * Represent class for a checking version of configs.
 */
public interface ParamsVersionCheck<C, T> {

    /**
     * Getting dateFormat in string value.
     * @return date format what you chose.
     */
    String getSimpleDateFormat();

    /**
     * Get actually version of config in root directory /plugins.
     */
    String getCurrentVersion(C configType, final @NotNull T currentYaml);

    /**
     * Get actually version of config from jar of plugin.
     */
    String getNewVersion(C configType, final @NotNull T yamlConfiguration);

    /**
     * @param version get a version for checking if actual number or not.
     * @return checked matches character is number.
     */
    boolean checkMatches(@NotNull String version);

}
