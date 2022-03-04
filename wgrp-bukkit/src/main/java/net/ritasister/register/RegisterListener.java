package net.ritasister.register;

import net.ritasister.listener.protect.RegionProtect;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;
import org.bukkit.plugin.PluginManager;

import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class RegisterListener {
	
	public static void RegisterEvents(PluginManager pm, UtilConfig utilConfig, UtilConfigMessage utilConfigMessage) {
		try{
			final RegionProtect creativeListener = new RegionProtect(WorldGuardRegionProtect.instance, utilConfig, utilConfigMessage);
			pm.registerEvents(creativeListener, WorldGuardRegionProtect.instance);
			
			RSLogger.info("&2All listeners load successfully!");
		}catch(Exception e){
            RSLogger.err("Could load all listeners... We have a error!");
            e.fillInStackTrace();
        }
	}
}