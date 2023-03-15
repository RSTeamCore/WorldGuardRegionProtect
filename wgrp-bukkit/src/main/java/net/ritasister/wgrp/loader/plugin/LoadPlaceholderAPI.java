package net.ritasister.wgrp.loader.plugin;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.papi.PlaceholderAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LoadPlaceholderAPI implements LoadPlugin {

    private final WorldGuardRegionProtect wgRegionProtect;

    public void loadPlugin() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (plugin != null && plugin.isEnabled()) {
            try {
                WGRPContainer.getLogger().info(String.format("Plugin: %s loaded successful!.", plugin.getName()));
                new PlaceholderAPIExpansion(wgRegionProtect).register();
                isPlaceholderAPIEnabled(true);
            } catch (NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                WGRPContainer.getLogger().error(exception.getMessage());
            }
        }
    }

    public boolean isPlaceholderAPIEnabled(boolean placeholderAPIEnabled) {
        return placeholderAPIEnabled;
    }

}
