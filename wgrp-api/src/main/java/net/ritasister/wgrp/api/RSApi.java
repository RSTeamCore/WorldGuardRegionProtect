package net.ritasister.wgrp.api;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;

/**
 * Utility api class for other classes to use the necessary methods and other.
 *
 * @param <E>
 * @param <P>
 * @param <C>
 */
public interface RSApi<E, P, C> {

    /**
     * Check if a player has permission for use Listener.
     *
     * @param player who send this command.
     * @param perm   permission to check.
     * @return can a player use this listener.
     */
    boolean isPlayerListenerPermission(P player, UtilPermissions perm);

    /**
     * Check if an entity has permission for use Listener.
     *
     * @param entity who send this command.
     * @param perm   permission to check.
     * @return can an entity use this listener.
     */
    boolean isEntityListenerPermission(E entity, UtilPermissions perm);

    /**
     * Send notification to admin.
     *
     * @param player        player object.
     * @param playerName    player name.
     * @param senderCommand name command if player attempt to use in a region.
     * @param regionName    the region name, if player attempts to use command in a region.
     */
    void notify(P player, String playerName, String senderCommand, String regionName);

    /**
     *
     * @param cancellable
     * @param entity
     * @param checkEntity
     */
    void entityCheck(C cancellable, E entity, E checkEntity);
}
