package net.ritasister.wgrp;

import net.ritasister.wgrp.rslibs.papi.PlaceholderAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LoadLibs {

    private final WorldGuardRegionProtect wgRegionProtect;

    public boolean PlaceholderAPIEnabled = false;

    public LoadLibs(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect = wgRegionProtect;
    }

    public void loadWorldGuard() {
        final String s = "WorldGuard";
        final Plugin plg = wgRegionProtect.getWgrpBukkitPlugin().getServer().getPluginManager().getPlugin(s);
        if (plg != null && plg.isEnabled()) {
            try{
                msgSuccess();
            }catch(NullPointerException | ClassCastException | NoClassDefFoundError ex){
                final Throwable exception = null;
                wgRegionProtect.getRsApi().getLogger().error(exception.getMessage());
            }
        }
    }

    public void loadPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPIExpansion(wgRegionProtect).register(); PlaceholderAPIEnabled=true;
        }
    }
	private void msgSuccess() {
        wgRegionProtect.getRsApi().getLogger().info("&2Plugin: WorldGuard loaded successful!.");
    }
}