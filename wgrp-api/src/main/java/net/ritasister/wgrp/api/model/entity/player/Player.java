package net.ritasister.wgrp.api.model.entity.player;

import net.ritasister.wgrp.api.model.entity.Entity;
import net.ritasister.wgrp.api.model.location.World;

/**
 * InDev...
 */
public interface Player extends Entity {

    /**
     * Gets the unique identifier for this player.
     *
     * @return the unique identifier
     */
    String getUniqueId();

    /**
     * Gets the name of the player.
     *
     * @return the player's name
     */
    String getName();

    /**
     * Gets the world where the player is currently located.
     *
     * @return the world where the player is currently located
     */
    World getWorld();

    /**
     * Sends a message to the player.
     *
     * @param message the message to send
     */
    <T> void sendMessage(T message);

    /**
     * Checks if the player has the given permission.
     *
     * @param permission the name of the permission
     * @return true if the player has the permission, false otherwise
     */
    boolean hasPermission(String permission);

    /**
     * Kicks the player from the platform with a reason.
     *
     * @param reason the reason for the kick
     */
    void kick(String reason);

    /**
     * Checks if the player is currently online.
     *
     * @return true if the player is online, false otherwise
     */
    boolean isOnline();

}
