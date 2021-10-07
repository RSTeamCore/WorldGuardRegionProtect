package net.ritasister.util;

import org.bukkit.permissions.*;

public interface UtilPermissions 
{
	public static final String 
	all_perm_plugin=("srp.*"),
	all_perm=("*"),

	reload_cfg=("wgrp.reload.cfg"),
	reload_msg_cfg=("wgrp.reload.cfg.msg"),

	regionProtectInfoAdmin=("wgrgprotect.info.admin"),
	regionProtect=("srp.regionprotect"),
	regionProtectAllow=("srp.regionprotectallow");
}