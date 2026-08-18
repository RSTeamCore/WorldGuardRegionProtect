package net.ritasister.wgrp.api.manager.regions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RegionAccessManager<L> {

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
}
