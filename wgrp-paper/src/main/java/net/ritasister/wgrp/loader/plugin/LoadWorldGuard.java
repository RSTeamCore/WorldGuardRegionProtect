package net.ritasister.wgrp.loader.plugin;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Utility class for load WorldGuard by WorldGuardRegionProtect.
 */
public class LoadWorldGuard {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public LoadWorldGuard(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    /**
     * Load PlaceholderAPI.
     */
    public void loadPlugin() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin != null && plugin.isEnabled()) {
            try {
                wgrpBukkitPlugin.getPluginLogger().info(String.format("Plugin: %s loaded successful!.", plugin.getName()));
            } catch (NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                wgrpBukkitPlugin.getPluginLogger().severe(exception.getMessage());
            }
        }
    }

}
