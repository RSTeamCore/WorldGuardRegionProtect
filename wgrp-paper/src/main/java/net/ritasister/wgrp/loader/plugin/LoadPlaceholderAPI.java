package net.ritasister.wgrp.loader.plugin;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import net.ritasister.wgrp.rslibs.papi.PlaceholderAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LoadPlaceholderAPI implements LoadPluginManager {

    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;

    public LoadPlaceholderAPI(final WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        this.wgrpBukkitBase = wgrpBukkitBase;
    }

    @Override
    public void loadPlugin() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (plugin != null && plugin.isEnabled()) {
            try {
                Bukkit.getLogger().info(String.format("Plugin: %s loaded successful!.", plugin.getName()));
                new PlaceholderAPIExpansion(wgrpBukkitBase).register();
                isPlaceholderAPIEnabled(true);
            } catch (NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                Bukkit.getLogger().severe(exception.getMessage());
            }
        }
    }

    public boolean isPlaceholderAPIEnabled(boolean placeholderAPIEnabled) {
        return placeholderAPIEnabled;
    }

}
