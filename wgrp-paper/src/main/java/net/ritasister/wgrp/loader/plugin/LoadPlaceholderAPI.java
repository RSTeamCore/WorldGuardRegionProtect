package net.ritasister.wgrp.loader.plugin;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.papi.PlaceholderAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Utility class for load PlaceholderAPI by WorldGuardRegionProtect.
 */
public class LoadPlaceholderAPI {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public LoadPlaceholderAPI(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    /**
     * Load PlaceholderAPI.
     */
    public void loadPlugin() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (plugin != null && plugin.isEnabled()) {
            try {
                wgrpBukkitPlugin.getPluginLogger().info(String.format("Plugin: %s loaded successful!.", plugin.getName()));
                new PlaceholderAPIExpansion(wgrpBukkitPlugin.getWgrpBukkitBase()).register();
                isPlaceholderAPIEnabled(true);
            } catch (NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                wgrpBukkitPlugin.getPluginLogger().severe(exception.getMessage());
            }
        }
    }

    /**
     * Check if PlaceholderAPI enabled.
     */
    public boolean isPlaceholderAPIEnabled(boolean placeholderAPIEnabled) {
        return placeholderAPIEnabled;
    }

}
