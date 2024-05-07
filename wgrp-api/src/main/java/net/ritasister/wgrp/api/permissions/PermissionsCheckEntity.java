package net.ritasister.wgrp.api.permissions;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;

/**
 * Utility api class for check permissions for only entity
 */
public interface PermissionsCheckEntity<E> {

    /**
     * Check if an entity has permission for use Listener.
     *
     * @param entity who send this command.
     * @param perm   permission to check.
     * @return can an entity use this listener.
     * @since 0.3.4
     */
    boolean isEntityListenerPermission(E entity, UtilPermissions perm);

}
