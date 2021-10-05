package net.ritasister.util;

import org.bukkit.permissions.*;

public enum UtilPermissions 
{
	all_perm_plugin("aqualimecommand.*"),
	all_perm("*"),
	
	reload_cfg("srp.reload.cfg"),
	reload_msg_cfg("srp.reload.cfg.msg"),
	
	serverRegionProtect("srp.serverregionprotect"),
	serverRegionProtectAllow("srp.serverregionallow");
	
	private String $perm;

    private UtilPermissions(String $perm) 
    {
        this.$perm=$perm;
    }
    public String getPerm() 
    {
        return $perm;
    }
}