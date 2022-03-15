package net.ritasister.wgrp;

import net.ritasister.rslibs.api.RSLogger;
import org.bukkit.plugin.Plugin;

public class LoadLibs {

	@SuppressWarnings("null")
	public static void loadWorldGuard() {
        final String s = "WorldGuard";
        final Plugin plg = WorldGuardRegionProtect.getInstance().getServer().getPluginManager().getPlugin(s);
        if (plg != null && plg.isEnabled()) {
            try{
                msgSuccess();
            }catch(NullPointerException | ClassCastException | NoClassDefFoundError ex){
                final Throwable t = null;
                final Throwable e = t;
                RSLogger.err(e.getMessage());
            }
        }
    }
	private static void msgSuccess() {
        RSLogger.info("&2Plugin: WorldGuard loaded successful!.");
    }
}