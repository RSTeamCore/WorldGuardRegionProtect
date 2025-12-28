package net.ritasister.wgrp.rslibs.permissions;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum UtilPermissions {

    NULL_PERM(""),
    PERMISSION_STAR("*"),

    // Command permissions
    COMMAND_WGRP("wgrp.command.wgrpbase"),
    COMMAND_WGRP_RELOAD_CONFIGS("wgrp.command.reload"),
    COMMAND_SPY_INSPECT_ADMIN("wgrp.command.spy"),
    COMMAND_ADD_REGION("wgrp.command.addregion"),
    COMMAND_REMOVE_REGION("wgrp.command.removeregion"),
    COMMAND_PROTECT("wgrp.command.protect"),
    COMMAND_UNPROTECT("wgrp.command.unprotect"),

    // Listener permissions
    ADMIN_RIGHT("wgrp.admin"),
    REGION_PROTECT("wgrp.regionprotect"),
    REGION_PROTECT_NOTIFY_ADMIN("wgrp.notify.admin"),
    SPY_INSPECT_FOR_SUSPECT("wgrp.spy.suspect"),
    SPY_INSPECT_ADMIN_LISTENER("wgrp.spy");

    private final String permissionName;
    private static final Map<String, UtilPermissions> PERMISSIONS_MAP = new HashMap<>();

    static {
        for (UtilPermissions permissions : values()) {
            if (permissions.permissionName != null) {
                PERMISSIONS_MAP.put(permissions.name().toLowerCase(Locale.ROOT), permissions);
            }
        }
    }

    @ApiStatus.Internal
    @Contract("null -> null")
    @Nullable
    public static UtilPermissions getPermission(String permissionName) {
        if (permissionName == null) {
            return null;
        }
        return PERMISSIONS_MAP.get(permissionName.toLowerCase(Locale.ROOT));
    }

    UtilPermissions(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public boolean equalsPermission(String permission) {
        return this.permissionName.equalsIgnoreCase(permission);
    }

    public static UtilPermissions fromPermissionName(String permission) {
        for (UtilPermissions perm : UtilPermissions.values()) {
            if (perm.getPermissionName().equalsIgnoreCase(permission)) {
                return perm;
            }
        }
        return NULL_PERM;
    }

    public static boolean isValid(String permission) {
        return fromPermissionName(permission) != NULL_PERM;
    }
}
