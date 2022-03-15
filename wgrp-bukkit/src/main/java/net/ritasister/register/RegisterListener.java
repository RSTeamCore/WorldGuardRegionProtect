package net.ritasister.register;

import net.ritasister.listener.protect.RegionProtect;
import net.ritasister.rslibs.api.RSApi;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class RegisterListener {
	
	public void RegisterEvents(PluginManager pm) {
		try{
			if(!RSApi.isVersion_V1_17()) {
				final RegionProtect regionProtect = new RegionProtect(WorldGuardRegionProtect.getInstance());
				pm.registerEvents(regionProtect, WorldGuardRegionProtect.getInstance());
				Bukkit.getConsoleSender().sendMessage("Register listener for mc 1.17");
			}else if(!RSApi.isVersion_V1_13()) {
				final RegionProtect regionProtect_V1_16 = new RegionProtect(WorldGuardRegionProtect.getInstance());
				pm.registerEvents(regionProtect_V1_16, WorldGuardRegionProtect.getInstance());
				Bukkit.getConsoleSender().sendMessage("Register listener for mc 1.13-1.16");
			}
			RSLogger.info("&2All listeners load successfully!");
		}catch(Exception e){
            RSLogger.err("Could load all listeners... We have a error!");
            e.fillInStackTrace();
        }
	}
}