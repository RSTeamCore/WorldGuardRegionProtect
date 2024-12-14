package net.ritasister.wgrp.api.model.permissions;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.jetbrains.annotations.ApiStatus;

/**
 * Utility API class for checking permissions specifically for entities.
 */
@Deprecated
public interface PermissionsCheckEntity<E> {

    /**
     * Checks whether the given entity has the required permission to use a listener.
     *
     * @param entity the entity attempting the action.
     * @param perm   the permission to be checked.
     * @return {@code true} if the entity has the required permission, {@code false} otherwise.
     * @since 0.3.4
     */
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion = "2.0.0")
    boolean isEntityListenerPermission(E entity, UtilPermissions perm);

}
