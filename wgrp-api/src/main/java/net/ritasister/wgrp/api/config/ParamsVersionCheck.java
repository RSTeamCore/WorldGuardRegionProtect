package net.ritasister.wgrp.api.config;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a class for checking the version of configuration files.
 *
 * @param <C> The type representing the configuration type.
 * @param <T> The type representing the configuration content (e.g., YAML configuration).
 */
public interface ParamsVersionCheck<C, T> {

    /**
     * Retrieves the date format as a string.
     *
     * @return A string representing the chosen date format.
     */
    String getDateFormat();

    /**
     * Retrieves the current version of the configuration from the root directory (/plugins).
     *
     * @param configType  The type of the configuration being checked.
     * @param currentYaml The current configuration content.
     * @return The current version of the configuration as a string.
     */
    String getCurrentVersion(C configType, final @NotNull T currentYaml);

    /**
     * Retrieves the new version of the configuration from the plugin's jar file.
     *
     * @param configType        The type of the configuration being checked.
     * @param yamlConfiguration The configuration content from the plugin's jar file.
     * @return The new version of the configuration as a string.
     */
    String getNewVersion(C configType, final @NotNull T yamlConfiguration);

    /**
     * Validates if the provided version string starts with a number.
     *
     * @param version The version string to check.
     * @return {@code true} if the version starts with a numeric character, {@code false} otherwise.
     */
    boolean checkMatches(@NotNull String version);

}
