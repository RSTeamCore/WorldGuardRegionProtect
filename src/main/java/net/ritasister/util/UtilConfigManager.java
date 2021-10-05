package net.ritasister.util;

import net.ritasister.srp.SRPLogger;
import net.ritasister.srp.ServerRegionProtect;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;

public class UtilConfigManager 
{
	
	private static UtilLoadConfig utilConfigManager = new UtilLoadConfig(ServerRegionProtect.instance);
	
	public static void loadConfig()
	{	
		ServerRegionProtect.utilConfig = new UtilConfig();
		
		utilConfigManager.LoadMSGConfig(ServerRegionProtect.instance, true);
		ServerRegionProtect.utilConfigMessage = new UtilConfigMessage();
		
		SRPLogger.info("Все конфиги успешно загружены.");
	}
}