package net.ritasister.wgrp.api.model.permissions;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;

public interface PermissionCheck {

    /**
     * Checks if the specified player has the required permission.
     *
     * @param player the player to be checked.
     * @param perm   the permission to check against.
     * @return {@code true} if the player has the specified permission; {@code false} otherwise.
     * @since 1.7.1.21
     */
    boolean hasPlayerPermission(Object player, UtilPermissions perm);

    /**
     * Checks if the specified entity has the required permission.
     *
     * @param entity the entity to be checked.
     * @param perm   the permission to check against.
     * @return {@code true} if the entity has the specified permission; {@code false} otherwise.
     * @since 1.7.1.21
     */
    boolean hasEntityPermission(Object entity, UtilPermissions perm);    
}
