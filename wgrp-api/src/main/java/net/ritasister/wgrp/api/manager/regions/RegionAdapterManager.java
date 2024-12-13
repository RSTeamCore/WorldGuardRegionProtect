package net.ritasister.wgrp.api.manager.regions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A region adapter manager for simplified interaction with the WorldGuard API.
 *
 * @param <L> The type representing a location.
 * @param <P> The type representing a player.
 */
public interface RegionAdapterManager<L, P> {

    /**
     * Determines if a player is the owner of a region based on region names from a map and their associated lists.
     *
     * @param location The location to check for region ownership.
     * @param regions  A map containing region names and their associated lists of regions.
     * @param uniqueId The UUID of the player whose ownership is being validated.
     * @return {@code true} if the player is the owner of the region; {@code false} otherwise.
     * @since 1.6.21
     */
    boolean isOwnerRegion(@NotNull L location, @NotNull Map<String, List<String>> regions, UUID uniqueId);

    /**
     * Determines if a player is the owner of a specific region.
     *
     * @param location The location to check for region ownership.
     * @param uniqueId The UUID of the player whose ownership is being validated.
     * @return {@code true} if the player is the owner of the region; {@code false} otherwise.
     * @since 1.7.21
     */
    boolean isOwnerRegion(@NotNull L location, UUID uniqueId);

    /**
     * Determines if a player is a member of a region based on region names from a map and their associated lists.
     *
     * @param location The location to check for region membership.
     * @param regions  A map containing region names and their associated lists of regions.
     * @param uniqueId The UUID of the player whose membership is being validated.
     * @return {@code true} if the player is a member of the region; {@code false} otherwise.
     * @since 1.6.21
     */
    boolean isMemberRegion(@NotNull L location, @NotNull Map<String, List<String>> regions, UUID uniqueId);

    /**
     * Determines if a player is a member of a specific region.
     *
     * @param location The location to check for region membership.
     * @param uniqueId The UUID of the player whose membership is being validated.
     * @return {@code true} if the player is a member of the region; {@code false} otherwise.
     * @since 1.7.21
     */
    boolean isMemberRegion(@NotNull L location, UUID uniqueId);

    /**
     * Retrieves the priority of a region at the specified location.
     *
     * @param location The location where the region priority is being retrieved.
     * @return An integer representing the priority of the region.
     * @since 1.10.21
     */
    int getPriorityRegion(@NotNull L location);

    /**
     * Checks if an object is located within a region, based on region names from a map.
     *
     * @param location The location of the object being checked.
     * @param regions  A map containing region names and their associated lists of regions.
     * @return {@code true} if the object is within a region; {@code false} otherwise.
     * @since 0.3.4
     */
    boolean checkStandingRegion(L location, Map<String, List<String>> regions);

    /**
     * Checks if an object is located within any region.
     *
     * @param location The location of the object being checked.
     * @return {@code true} if the object is within a region; {@code false} otherwise.
     * @since 0.3.4
     */
    boolean checkStandingRegion(L location);

    /**
     * Retrieves the name of the region in which the specified location is located.
     *
     * @param location The location being checked for a protected region name.
     * @return The name of the region as a {@code String}.
     * @since 0.7.1
     */
    String getProtectRegionName(L location);

    /**
     * Retrieves the name of the region based on the player's current selection in WorldEdit.
     *
     * @param player The player making the selection.
     * @return The name of the selected region as a {@code String}.
     * @since 0.7.6
     */
    String getProtectRegionNameBySelection(final P player);

}
