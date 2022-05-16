package net.ritasister.rslibs.permissions;

public enum IUtilPermissions {

	COMMAND_WGRP("wgrp.command.wgrpbase"),
	REGION_PROTECT("wgrp.command.wgrpbase"),
	REGION_PROTECT_NOTIFY_ADMIN("wgrp.notify.admin"),
	SPY_INSPECT_FOR_SUSPECT("wgrp.spy.spyinspectforsuspect"),
	SPY_ADMIN_FOR_SUSPECT("Owner");

	public final String permissionName;

	IUtilPermissions(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionName() {
		return permissionName;
	}
}