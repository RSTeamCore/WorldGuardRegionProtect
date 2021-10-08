package net.ritasister.util;

import org.bukkit.permissions.*;

public interface IUtilPermissions 
{
	public static final String 
	all_perm_plugin=("wgrp.*"),
	all_perm=("*"),

	reload_cfg=("wgrp.reload.cfg"),
	reload_msg_cfg=("wgrp.reload.cfg.msg"),

	regionProtectNotifyAdmin=("wgrp.notify.admin"),
	regionProtect=("wgrp.regionprotect"),
	regionProtectAllow=("wgrp.regionprotectallow");
}