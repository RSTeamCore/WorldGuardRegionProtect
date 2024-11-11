package net.ritasister.wgrp.loader.plugin;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.papi.PlaceholderAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Utility class for load PlaceholderAPI by WorldGuardRegionProtect.
 */
public class LoadPlaceholderAPI {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public LoadPlaceholderAPI(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    /**
     * Load methods for catch PlaceholderAPI.
     */
    public void loadPlugin() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (plugin != null && plugin.isEnabled()) {
            wgrpPlugin.getLogger().info(String.format("Plugin: %s loaded successful!.", plugin.getName()));
            new PlaceholderAPIExpansion(wgrpPlugin.getWgrpPaperBase()).register();
            isPlaceholderAPIEnabled(true);
        }
    }

    /**
     * Check if PlaceholderAPI enabled.
     */
    public boolean isPlaceholderAPIEnabled(boolean placeholderAPIEnabled) {
        return placeholderAPIEnabled;
    }

}
