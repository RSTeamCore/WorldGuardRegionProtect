package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.api.model.permissions.PermissionCheck;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class PlayerPermissionsImpl implements PermissionCheck {

    @Override
    public boolean hasPlayerPermission(final Object player, final UtilPermissions perm) {
        if (player instanceof Player playerCheck) {
            return !playerCheck.hasPermission(perm.getPermissionName());
        }
        return false;
    }

    @Override
    public boolean hasEntityPermission(final Object entity, final UtilPermissions perm) {
        if (entity instanceof Entity entityCheck) {
            return !entityCheck.hasPermission(perm.getPermissionName());
        }
        return false;
    }

}
