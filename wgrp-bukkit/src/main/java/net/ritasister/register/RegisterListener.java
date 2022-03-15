package net.ritasister.register;

import net.ritasister.listener.protect.RegionProtect;
import net.ritasister.rslibs.api.RSApi;
import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.plugin.PluginManager;

public class RegisterListener {
	
	public static void RegisterEvents(PluginManager pm) {
		try{
			if(RSApi.isVersionV1_16() && RSApi.isVersionV1_17()) {
				final RegionProtect regionProtect = new RegionProtect(WorldGuardRegionProtect.getInstance());
				pm.registerEvents(regionProtect, WorldGuardRegionProtect.getInstance());
				RSLogger.info("&6You are using server version 1.16. Dont are used any features from 1.17+");
			}else{
				RSLogger.info("&6You are using server version 1.17 or higher. Used all last feauters of these versions.");
			}
			RSLogger.info("&2All listeners load successfully!");
		}catch(Exception e){
            RSLogger.err("Could load all listeners... We have a error!");
            e.fillInStackTrace();
        }
	}
}