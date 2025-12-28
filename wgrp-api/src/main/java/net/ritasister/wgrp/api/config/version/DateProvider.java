package net.ritasister.wgrp.api.config.version;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for providing the current date in a specific format.
 */
public interface DateProvider {

    /**
     * Retrieves the current date in a specific format.
     *
     * @return The current date as a string.
     */
    @NotNull String getCurrentDate();
}
