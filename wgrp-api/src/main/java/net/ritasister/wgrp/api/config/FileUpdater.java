package net.ritasister.wgrp.api.config;

import java.io.File;

/**
 * Interface for updating configuration and language files.
 *
 * @param <P> The type of the plugin instance.
 */

public interface FileUpdater<P> {

    /**
     * Updates the main configuration file.
     *
     * @param plugin      The plugin instance.
     * @param currentFile The current configuration file to be updated.
     */
    void updateConfig(P plugin, File currentFile);

    /**
     * Updates the language file for the specified language.
     *
     * @param plugin      The plugin instance.
     * @param currentFile The current language file to be updated.
     * @param lang        The language code (e.g., "en", "fr").
     */
    void updateLang(P plugin, File currentFile, String lang);
}
