package net.ritasister.wgrp.api.messaging;

public interface MessagingService<P> {

    /**
     * Send any notification for only admin
     *
     * @param player        player object.
     * @param playerName    player name.
     * @param senderCommand name command if player attempt to use in a region.
     * @param regionName    the region name, if player attempts to use command in a region.
     */
    void notify(P player, String playerName, String senderCommand, String regionName);

    /**
     * Send any notification for only console
     *
     * @param playerName    player name.
     * @param senderCommand name command if Player attempt to use in a region.
     * @param regionName    region name, if Player attempts to use command in a region.
     */
    void notify(String playerName, String senderCommand, String regionName);

}
