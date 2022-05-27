package net.ritasister.wgrp.rslibs.permissions;

public enum UtilPermissions {

	NULL_PERM(""),
	PERMISSION_STAR("*"),
	//Command permission
	COMMAND_WGRP("wgrp.command.wgrpbase"),
	COMMAND_WGRP_RELOAD_CONFIGS("wgrp.command.reload"),
	COMMAND_SPY_INSPECT_ADMIN("wgrp.command.spy"),

	//Listeners permission
	REGION_PROTECT("wgrp.regionprotect"),
	REGION_PROTECT_NOTIFY_ADMIN("wgrp.notify.admin"),
	SPY_INSPECT_FOR_SUSPECT("wgrp.spy.suspect"),
	SPY_INSPECT_ADMIN_LISTENER("wgrp.spy");

	public final String permissionName;

	UtilPermissions(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionName() {
		return permissionName;
	}
}