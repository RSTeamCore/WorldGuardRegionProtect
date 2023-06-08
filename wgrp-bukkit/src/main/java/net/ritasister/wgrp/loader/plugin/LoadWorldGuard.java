package net.ritasister.wgrp.loader.plugin;

import net.ritasister.wgrp.WGRPContainer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LoadWorldGuard implements LoadPluginManager {

    @Override
    public void loadPlugin() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin != null && plugin.isEnabled()) {
            try {
                WGRPContainer.getLogger().info(String.format("Plugin: %s loaded successful!.", plugin.getName()));
            } catch (NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                WGRPContainer.getLogger().error(exception.getMessage());
            }
        }
    }

}
