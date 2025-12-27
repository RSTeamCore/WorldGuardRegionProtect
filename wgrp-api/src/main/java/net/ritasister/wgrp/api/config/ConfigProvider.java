package net.ritasister.wgrp.api.config;

/**
 * Interface for configuration providers handling region protection data.
 *
 * @param <P> The type of the plugin instance.
 */
public interface ConfigProvider<P, C> {

    /**
     * Initializes the configuration provider with the given plugin instance.
     *
     * @param plugin The plugin instance.
     */
    void init(P plugin);

    /**
     * Retrieves the loaded configuration.
     *
     * @return The configuration instance.
     */
    C getConfig();
}
