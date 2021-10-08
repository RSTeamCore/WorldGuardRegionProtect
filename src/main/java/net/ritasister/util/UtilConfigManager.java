package net.ritasister.util;

import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class UtilConfigManager 
{
	private static UtilLoadConfig utilConfigManager = new UtilLoadConfig(WorldGuardRegionProtect.instance);
	
	public static void loadConfig()
	{	
		WorldGuardRegionProtect.utilConfig = new UtilConfig();
		
		utilConfigManager.LoadMSGConfig(WorldGuardRegionProtect.instance, true);
		WorldGuardRegionProtect.utilConfigMessage = new UtilConfigMessage();
		RSLogger.info("&2All configs load successfully!");
	}
}