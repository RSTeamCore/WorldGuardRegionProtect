package net.ritasister.wgrp.api.manager.regions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface RegionLookup<L> {

    /**
     * Retrieves the priority of a region at the specified location.
     *
     * @param location The location to retrieve the region priority from.
     * @return An integer representing the priority of the region.
     * @since 1.10.21
     */
    int getPriorityRegion(@NotNull L location);

    /**
     * Checks if an object is located within any region.
     *
     * @param location The location to check.
     * @return {@code true} if the object is within a region; {@code false} otherwise.
     * @since 0.3.4
     */
    boolean checkStandingRegion(@NotNull L location);

    /**
     * Checks if an object is located within a region, based on region names from a map.
     *
     * @param location The location to check.
     * @param regions  A map containing region names and their associated region lists.
     * @return {@code true} if the object is within a region; {@code false} otherwise.
     * @since 0.3.4
     */
    boolean checkStandingRegion(@NotNull L location, @NotNull Map<String, List<String>> regions);

    /**
     * Retrieves the name of the region in which the specified location is located.
     *
     * @param location The location to retrieve the region name from.
     * @return The name of the region as a {@code String}.
     * @since 0.7.1
     */
    String getProtectRegionName(@NotNull L location);
}
