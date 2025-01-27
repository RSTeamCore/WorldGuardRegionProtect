package net.ritasister.wgrp.api.model.permissions;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.jetbrains.annotations.ApiStatus;

/**
 * Utility API class for checking permissions for both players and entities.
 */
@Deprecated(since = "1.8.2.21", forRemoval = true)
public interface PermissionsCheck<P, E> extends PermissionsCheckEntity<E> {

    /**
     * Checks if a player has the required permission to use a listener.
     *
     * @param player the player attempting the action.
     * @param perm   the permission to be checked.
     * @return {@code true} if the player has the required permission, {@code false} otherwise.
     * @since 0.3.4
     */
    @Deprecated
    boolean isPlayerListenerPermission(P player, UtilPermissions perm);

}
