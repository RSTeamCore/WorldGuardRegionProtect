package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import org.bukkit.Bukkit;

public class PluginDisabler {

    final WorldGuardRegionProtectPaperPlugin plugin;

    public PluginDisabler(WorldGuardRegionProtectPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void disableWithMessage(String reason) {
        if (plugin.getWgrpPaperBase().isEnabled()) {
            plugin.getLogger().severe(String.format("Disabling plugin '%s' due to: %s", plugin.getWgrpPaperBase().getName(), reason));
            Bukkit.getServer().getPluginManager().disablePlugin(plugin.getWgrpPaperBase());
        } else {
            plugin.getLogger().warn(String.format("Attempted to disable plugin '%s', but it was already disabled.", plugin.getWgrpPaperBase().getName()));
        }
    }
}
