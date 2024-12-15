package net.ritasister.wgrp.loader.plugin;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.papi.PlaceholderAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LoadPlaceholderAPI {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public LoadPlaceholderAPI(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    public void loadPlugin() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (plugin != null && plugin.isEnabled()) {
            wgrpPlugin.getLogger().info(String.format("PlaceholderAPI detected. Plugin: %s loaded successfully.", plugin.getName()));
            try {
                new PlaceholderAPIExpansion(wgrpPlugin).register();
                isPlaceholderAPIEnabled(true);
                wgrpPlugin.getLogger().info("PlaceholderAPI expansion registered successfully.");
            } catch (Exception e) {
                wgrpPlugin.getLogger().severe("Failed to register PlaceholderAPI expansion: " + e.getMessage());
            }
        } else {
            wgrpPlugin.getLogger().warn("PlaceholderAPI is not installed or enabled. Placeholders will not function.");
        }
    }

    public boolean isPlaceholderAPIEnabled(boolean placeholderAPIEnabled) {
        return placeholderAPIEnabled;
    }

}
