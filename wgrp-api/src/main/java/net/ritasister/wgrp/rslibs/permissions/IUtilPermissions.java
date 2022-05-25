package net.ritasister.wgrp.rslibs.permissions;

public enum IUtilPermissions {

	COMMAND_WGRP("wgrp.command.wgrpbase"),
	COMMAND_WGRP_RELOAD_CONFIGS("wgrp.command.reload"),
	REGION_PROTECT("wgrp.regionprotect"),
	REGION_PROTECT_NOTIFY_ADMIN("wgrp.notify.admin"),
	SPY_INSPECT_FOR_SUSPECT("wgrp.spy.suspect"),
	SPY_INSPECT_ADMIN_COMMAND("wgrp.command.spy.admin"),
	SPY_INSPECT_ADMIN_LISTENER("wgrp.spy.admin");

	public final String permissionName;

	IUtilPermissions(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionName() {
		return permissionName;
	}
}