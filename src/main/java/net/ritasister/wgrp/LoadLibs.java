package net.ritasister.wgrp;

import org.bukkit.plugin.*;
import com.sk89q.worldguard.bukkit.*;
import net.ritasister.rslibs.api.RSLogger;

public class LoadLibs 
{
	@SuppressWarnings("null")
	public static WorldGuardPlugin loadWorldGuard() 
	{
        final String s = "WorldGuard";
        final Plugin plg = WorldGuardRegionProtect.instance.getServer().getPluginManager().getPlugin(s);
        if (plg != null && plg.isEnabled()) 
        {
            try{
                msgSuccess();
                return (WorldGuardPlugin)plg;
            }catch(NullPointerException | ClassCastException | NoClassDefFoundError ex){
                final Throwable t = null;
                final Throwable e = t;
                RSLogger.err(e.getMessage());
            }
        }
        return null;
    }
	private static void msgSuccess()
	{
        RSLogger.info("&2Plugin: WorldGuard loaded successful!.");
    }
}