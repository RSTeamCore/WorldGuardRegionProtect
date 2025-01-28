package net.ritasister.wgrp.api.manager.regions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A region adapter manager for simplified interaction with the WorldGuard API.
 *
 * @param <L> Represents the location type (e.g., WorldGuard location object).
 * @param <P> Represents the player type (e.g., Bukkit Player object).
 */
public interface RegionAdapterManager<L, P, W> {

    /**
     * Determines if a player is the owner of a region based on region names from a map.
     *
     * @param location The location to check for region ownership.
     * @param regions  A map containing region names and their associated region lists.
     * @param uniqueId The UUID of the player to validate ownership.
     * @return {@code true} if the player is the owner of a region; {@code false} otherwise.
     * @since 1.6.21
     */
    boolean isOwnerRegion(@NotNull L location, @NotNull Map<String, List<String>> regions, @NotNull UUID uniqueId);

    /**
     * Determines if a player is the owner of a specific region.
     *
     * @param location The location to check for region ownership.
     * @param uniqueId The UUID of the player to validate ownership.
     * @return {@code true} if the player is the owner of a region; {@code false} otherwise.
     * @since 1.7.21
     */
    boolean isOwnerRegion(@NotNull L location, @NotNull UUID uniqueId);

    /**
     * Determines if a player is a member of a region based on region names from a map.
     *
     * @param location The location to check for membership.
     * @param regions  A map containing region names and their associated region lists.
     * @param uniqueId The UUID of the player to validate membership.
     * @return {@code true} if the player is a member of a region; {@code false} otherwise.
     * @since 1.6.21
     */
    boolean isMemberRegion(@NotNull L location, @NotNull Map<String, List<String>> regions, @NotNull UUID uniqueId);

    /**
     * Determines if a player is a member of a specific region.
     *
     * @param location The location to check for membership.
     * @param uniqueId The UUID of the player to validate membership.
     * @return {@code true} if the player is a member of a region; {@code false} otherwise.
     * @since 1.7.21
     */
    boolean isMemberRegion(@NotNull L location, @NotNull UUID uniqueId);

    /**
     * Retrieves the priority of a region at the specified location.
     *
     * @param location The location to retrieve the region priority from.
     * @return An integer representing the priority of the region.
     * @since 1.10.21
     */
    int getPriorityRegion(@NotNull L location);

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
     * Checks if an object is located within any region.
     *
     * @param location The location to check.
     * @return {@code true} if the object is within a region; {@code false} otherwise.
     * @since 0.3.4
     */
    boolean checkStandingRegion(@NotNull L location);

    /**
     * Retrieves the name of the region in which the specified location is located.
     *
     * @param location The location to retrieve the region name from.
     * @return The name of the region as a {@code String}.
     * @since 0.7.1
     */
    String getProtectRegionName(@NotNull L location);

    /**
     * Retrieves the protected region name for the specified region in the given world.
     *
     * <p>This method searches for a protected region in the specified world using the region's name.
     * If the region exists, its name is returned. If the region is not found,
     * an exception might be thrown or a default value may be returned depending on the implementation.</p>
     *
     * @param name the name of the region for which the protected region name is to be retrieved
     * @param world the world in which to search for the protected region
     * @return the name of the protected region if found, otherwise a default value or exception might be returned
     * @since 1.8.3.21
     */
    String getProtectRegionName(@NotNull String name, W world);

    /**
     * Retrieves the name of the region based on the player's current selection in WorldEdit.
     *
     * @param player The player making the selection.
     * @return The name of the selected region as a {@code String}.
     * @since 0.7.6
     */
    String getProtectRegionNameBySelection(@NotNull P player);
}
