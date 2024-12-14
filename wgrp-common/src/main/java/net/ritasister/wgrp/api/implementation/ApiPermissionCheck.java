package net.ritasister.wgrp.api.implementation;

import net.ritasister.wgrp.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.api.model.permissions.PermissionCheck;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;

public class ApiPermissionCheck implements PermissionCheck {

    private final WorldGuardRegionProtectPlugin plugin;

    public ApiPermissionCheck(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean hasPlayerPermission(final Object player, final UtilPermissions perm) {
        return plugin.getPermissionCheck().hasPlayerPermission(player, perm);
    }

    @Override
    public boolean hasEntityPermission(final Object entity, final UtilPermissions perm) {
        return plugin.getPermissionCheck().hasEntityPermission(entity, perm);
    }

}
