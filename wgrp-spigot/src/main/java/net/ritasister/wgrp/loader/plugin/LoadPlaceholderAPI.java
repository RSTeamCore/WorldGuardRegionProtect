package net.ritasister.wgrp.loader.plugin;

import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import net.ritasister.wgrp.rslibs.papi.PlaceholderAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@Slf4j
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
                log.info(String.format("Plugin: %s loaded successful!.", plugin.getName()));
                new PlaceholderAPIExpansion(wgrpBukkitBase).register();
                isPlaceholderAPIEnabled(true);
            } catch (NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                log.error(exception.getMessage());
            }
        }
    }

    public boolean isPlaceholderAPIEnabled(boolean placeholderAPIEnabled) {
        return placeholderAPIEnabled;
    }

}
