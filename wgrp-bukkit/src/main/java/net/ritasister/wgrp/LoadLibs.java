package net.ritasister.wgrp;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.rslibs.papi.PlaceholderAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LoadLibs implements ILoadLibs {

    private final WorldGuardRegionProtect wgRegionProtect;
    @Assisted public boolean placeholderAPIEnabled;

    public void loadWorldGuard() {
        final String s = "WorldGuard";
        final Plugin plg = wgRegionProtect.getWGRPBukkitPlugin().getServer().getPluginManager().getPlugin(s);
        if (plg != null && plg.isEnabled()) {
            try {
                msgSuccess();
            } catch(NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                wgRegionProtect.getLogger().error(Component.text(exception.getMessage()));
            }
        }
    }

    public void loadPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPIExpansion(wgRegionProtect).register(); placeholderAPIEnabled=true;
        }
    }
	private void msgSuccess() {
        wgRegionProtect.getLogger().info(Component.text("&2Plugin: WorldGuard loaded successful!."));
    }

    @Override
    public boolean isPlaceholderAPIEnabled() {
        return this.placeholderAPIEnabled;
    }
}