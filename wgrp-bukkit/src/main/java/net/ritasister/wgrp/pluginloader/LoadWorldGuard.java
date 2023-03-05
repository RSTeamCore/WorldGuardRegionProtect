package net.ritasister.wgrp.pluginloader;

import net.ritasister.wgrp.pluginloader.interfaces.LoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LoadWorldGuard implements LoadPlugin {

    @Override
    public void loadPlugin() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin != null && plugin.isEnabled()) {
            try {
                Bukkit.getLogger().info(String.format("Plugin: %s loaded successful!.", plugin.getName()));
            } catch (NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                Bukkit.getLogger().severe(exception.getMessage());
            }
        }
    }

}
