package net.ritasister.wgrp.api.messaging;

/**
 * Represent class for messaging for player or admin.
 * @param <P>
 */
public interface MessagingService<P> {

    /**
     * Send any notification for only admin
     *
     * @param player player object.
     * @param playerName player name.
     * @param senderCommand the name command if a player attempts to use in a region.
     * @param regionName the region name if a player attempts to use command in a region.
     * @since 0.7.1
     */
    void notify(P player, String playerName, String senderCommand, String regionName);

    /**
     * Send any notification for only console
     *
     * @param playerName player name.
     * @param senderCommand the name command if a player attempts to use in a region.
     * @param regionName region name if a player attempts to use command in a region.
     * @since 0.7.1
     */
    void notify(String playerName, String senderCommand, String regionName);

}
