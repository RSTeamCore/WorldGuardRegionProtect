package net.ritasister.wgrp;

import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.rslibs.papi.PlaceholderAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LoadLibs {

    private final WorldGuardRegionProtect wgRegionProtect;

    public LoadLibs(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect=wgRegionProtect;
    }

    public boolean PlaceholderAPIEnabled = false;

    public void loadWorldGuard() {
        final String s = "WorldGuard";
        final Plugin plg = wgRegionProtect.getWGRPBukkitPlugin().getServer().getPluginManager().getPlugin(s);
        if (plg != null && plg.isEnabled()) {
            try {
                msgSuccess();
            } catch(NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                wgRegionProtect.getLogger().error(Component.text(exception.getMessage()));
            }
        }
    }

    public void loadPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPIExpansion(wgRegionProtect).register(); PlaceholderAPIEnabled=true;
        }
    }
	private void msgSuccess() {
        wgRegionProtect.getLogger().info(Component.text("&2Plugin: WorldGuard loaded successful!."));
    }
}