package net.ritasister.register;

import org.bukkit.plugin.PluginManager;

import net.ritasister.listener.protect.RegionProtect;
import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class RegisterListener 
{
	
	public static void RegisterEvents(PluginManager pm)
	{
		try
		{
			final RegionProtect creativeListener = new RegionProtect(WorldGuardRegionProtect.instance);
			pm.registerEvents(creativeListener, WorldGuardRegionProtect.instance);
			
			RSLogger.info("&2All listeners load successfully!");
		}catch(Exception e){
            RSLogger.err("Could load all listeners... We have a error!");
            e.fillInStackTrace();
        }
	}
}