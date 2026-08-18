package net.ritasister.wgrp.api.model.entity;

import java.util.UUID;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents the profile of a player, regardless of their online status.
 */
public interface User extends Entity {

    /**
     * Gets the unique identifier (UUID) for this player.
     *
     * @return the unique identifier
     */
    @NonNull UUID getUniqueId();

    /**
     * Gets the last known name of the player.
     *
     * @return the player's name
     */
    @NonNull String getName();
}
