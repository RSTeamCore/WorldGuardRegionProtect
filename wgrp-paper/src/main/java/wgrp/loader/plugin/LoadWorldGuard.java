package wgrp.loader.plugin;

import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@Slf4j
public class LoadWorldGuard implements LoadPluginManager {


    @Override
    public void loadPlugin() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin != null && plugin.isEnabled()) {
            try {
                log.info(String.format("Plugin: %s loaded successful!.", plugin.getName()));
            } catch (NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                log.error(exception.getMessage());
            }
        }
    }

}
