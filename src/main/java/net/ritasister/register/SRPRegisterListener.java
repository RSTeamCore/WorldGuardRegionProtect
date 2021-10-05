package net.ritasister.register;

import org.bukkit.plugin.PluginManager;

import net.ritasister.listener.protect.RegionProtect;
import net.ritasister.srp.SRPLogger;
import net.ritasister.srp.ServerRegionProtect;

public class SRPRegisterListener 
{
	
	public static void RegisterEvents(PluginManager pm)
	{
		try
		{
			final RegionProtect creativeListener = new RegionProtect(ServerRegionProtect.instance);
			pm.registerEvents(creativeListener, ServerRegionProtect.instance);
			
			SRPLogger.info("Все события плагина были успешно зарегистрированы.");
		}catch(Exception e){
            SRPLogger.err("Не удалось зарегистрировать все события плагина.");
            e.fillInStackTrace();
        }
	}
}