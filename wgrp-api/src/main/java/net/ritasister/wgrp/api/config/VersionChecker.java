package net.ritasister.wgrp.api.config;

/**
 * Interface for checking the version of the plugin or configuration.
 *
 * @param <P> The type of the plugin instance.
 */
public interface VersionChecker<P> {

    /**
     * Checks the version of the plugin or configuration.
     *
     * @param plugin The plugin instance.
     */
    void check(P plugin);
}