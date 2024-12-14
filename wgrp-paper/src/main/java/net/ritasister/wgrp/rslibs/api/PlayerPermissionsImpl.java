package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.api.model.permissions.PermissionCheck;
import net.ritasister.wgrp.api.model.permissions.PermissionsCheck;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PlayerPermissionsImpl implements PermissionsCheck<Player, Entity>, PermissionCheck {
    
    @Override
    public boolean isPlayerListenerPermission(@NotNull Player player, @NotNull UtilPermissions perm) {
        return !player.hasPermission(perm.getPermissionName());
    }

    @Override
    public boolean isEntityListenerPermission(@NotNull Entity entity, @NotNull UtilPermissions perm) {
        return !entity.hasPermission(perm.getPermissionName());
    }

    @Override
    public boolean hasPlayerPermission(final Object player, final UtilPermissions perm) {
        if (player instanceof Player) {
            return ((Player) player).hasPermission(perm.getPermissionName());
        }
        return false;
    }

    @Override
    public boolean hasEntityPermission(final Object entity, final UtilPermissions perm) {
        if (entity instanceof Entity) {
            return ((Entity) entity).hasPermission(perm.getPermissionName());
        }
        return false;
    }

}
