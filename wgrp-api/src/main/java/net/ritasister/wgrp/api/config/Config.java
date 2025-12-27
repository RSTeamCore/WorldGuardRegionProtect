package net.ritasister.wgrp.api.config;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Config interface for managing region protection settings.
 */
public interface Config {

    /**
     * Gets the map of region protection settings.
     *
     * @return A map where the key is the region identifier and the value is a list of protection settings.
     */
    Map<String, List<String>> getRegionProtectMap();

    /**
     * Sets the map of region protection settings.
     *
     * @param value A map where the key is the region identifier and the value is a list of protection settings.
     */
    void setRegionProtectMap(@NotNull Map<String, List<String>> value);

    /**
     * Gets the map of region protection allow settings.
     *
     * @return A map where the key is the region identifier and the value is a list of allow settings.
     */
    Map<String, List<String>> getRegionProtectAllowMap();

    /**
     * Sets the map of region protection allow settings.
     *
     * @param value A map where the key is the region identifier and the value is a list of allow settings.
     */
    void setRegionProtectAllowMap(@NotNull Map<String, List<String>> value);

    /**
     * Gets the map of region protection only break allow settings.
     *
     * @return A map where the key is the region identifier and the value is a list of only break allow settings.
     */
    Map<String, List<String>> getRegionProtectOnlyBreakAllowMap();

    void setRegionProtectOnlyBreakAllow(@NotNull Map<String, List<String>> value);
}
