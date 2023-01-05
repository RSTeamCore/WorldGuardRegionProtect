package net.ritasister.wgrp;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.rslibs.interfaces.LoadLibs;
import net.ritasister.wgrp.rslibs.papi.PlaceholderAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LoadLibsImpl implements LoadLibs {

    private final WorldGuardRegionProtect wgRegionProtect;

    @Override
    public void loadWorldGuard() {
        final Plugin plugin = wgRegionProtect.getWGRPBukkitPlugin().getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin != null && plugin.isEnabled()) {
            try {
                msgSuccess();
            } catch (NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                wgRegionProtect.getLogger().error(exception.getMessage());
            }
        }
    }

    @Override
    public void loadPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPIExpansion(wgRegionProtect).register();
            isPlaceholderAPIEnabled(true);
        }
    }

    private void msgSuccess() {
        wgRegionProtect.getLogger().info("Plugin: WorldGuard loaded successful!.");
    }

    @Override
    public boolean isPlaceholderAPIEnabled(boolean placeholderAPIEnabled) {
        return placeholderAPIEnabled;
    }

}
