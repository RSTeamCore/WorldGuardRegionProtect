package net.ritasister.wgrp.rslibs.permissions;

public enum UtilPermissions {

	COMMAND_WGRP("wgrp.command.wgrpbase"),
	COMMAND_WGRP_RELOAD_CONFIGS("wgrp.command.reload"),
	REGION_PROTECT("wgrp.regionprotect"),
	REGION_PROTECT_NOTIFY_ADMIN("wgrp.notify.admin"),
	SPY_INSPECT_FOR_SUSPECT("wgrp.spy.suspect"),
	SPY_INSPECT_ADMIN_COMMAND("wgrp.command.spy"),
	SPY_INSPECT_ADMIN_LISTENER("wgrp.spy");

	public final String permissionName;

	UtilPermissions(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionName() {
		return permissionName;
	}
}