package net.ritasister.wgrp.api.config.version;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for reading the version of configuration files.
 *
 * @param <T> The type representing the configuration content (e.g., YAML configuration).
 */
public interface ConfigVersionReader<T, C> {

    /**
     * Retrieves the version of the given configuration.
     *
     * @param type   The type of the configuration being checked.
     * @param config The configuration content.
     * @return The version of the configuration as a string.
     */
    int getVersion(@NotNull T type, @NotNull C config);
}