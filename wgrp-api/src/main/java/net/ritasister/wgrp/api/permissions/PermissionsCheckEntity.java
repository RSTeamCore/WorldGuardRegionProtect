package net.ritasister.wgrp.api.permissions;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;

/**
 * Utility API class for checking permissions specifically for entities.
 */
public interface PermissionsCheckEntity<E> {

    /**
     * Checks whether the given entity has the required permission to use a listener.
     *
     * @param entity the entity attempting the action.
     * @param perm   the permission to be checked.
     * @return {@code true} if the entity has the required permission, {@code false} otherwise.
     * @since 0.3.4
     */
    boolean isEntityListenerPermission(E entity, UtilPermissions perm);

}
