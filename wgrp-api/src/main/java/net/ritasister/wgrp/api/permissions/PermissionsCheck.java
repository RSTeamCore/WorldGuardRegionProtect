package net.ritasister.wgrp.api.permissions;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;

/**
 * Utility api class for check permissions for only player and extends for entity
 */
public interface PermissionsCheck<P, E> extends PermissionsCheckEntity<E> {

    /**
     * Check if a player has permission for use Listener.
     *
     * @param player who send this command.
     * @param perm permission to check.
     * @return true otherwise false
     * @since 0.3.4
     */
    boolean isPlayerListenerPermission(P player, UtilPermissions perm);

}
