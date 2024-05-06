package wgrp.loader.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LoadWorldGuard implements LoadPluginManager {


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
