package net.ritasister.wgrp;

import org.bukkit.plugin.Plugin;

public class LoadLibs {

	public void loadWorldGuard() {
        final String s = "WorldGuard";
        final Plugin plg = WorldGuardRegionProtect.getInstance().getServer().getPluginManager().getPlugin(s);
        if (plg != null && plg.isEnabled()) {
            try{
                msgSuccess();
            }catch(NullPointerException | ClassCastException | NoClassDefFoundError ex){
                final Throwable exception = null;
                WorldGuardRegionProtect.getInstance().getRsApi().getLogger().error(exception.getMessage());
            }
        }
    }
	private static void msgSuccess() {
        WorldGuardRegionProtect.getInstance().rsApi.getLogger().info("&2Plugin: WorldGuard loaded successful!.");
    }
}