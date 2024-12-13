package net.ritasister.wgrp.api.messaging;

/**
 * Represents a messaging service for communicating with players or administrators.
 *
 * @param <P> the type representing the player object.
 */
public interface MessagingService<P> {

    /**
     * Sends a notification specifically to an administrator.
     *
     * @param player        the player object triggering the notification.
     * @param playerName    the name of the player triggering the notification.
     * @param senderCommand the command the player attempted to use in the region.
     * @param regionName    the name of the region where the command was attempted.
     * @since 0.7.1
     */
    void notify(P player, String playerName, String senderCommand, String regionName);

    /**
     * Sends a notification specifically to the console.
     *
     * @param playerName    the name of the player triggering the notification.
     * @param senderCommand the command the player attempted to use in the region.
     * @param regionName    the name of the region where the command was attempted.
     * @since 0.7.1
     */
    void notify(String playerName, String senderCommand, String regionName);

}
