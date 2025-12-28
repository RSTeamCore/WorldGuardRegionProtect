package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import org.bukkit.Bukkit;

public class PluginDisabler {

    final WorldGuardRegionProtectPaperPlugin plugin;

    public PluginDisabler(WorldGuardRegionProtectPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void disableWithMessage(String reason) {
        if (plugin.getBootstrap().getLoader().isEnabled()) {
            plugin.getLogger().severe(String.format("Disabling plugin '%s' due to: %s", plugin.getBootstrap().getLoader().getName(), reason));
            Bukkit.getServer().getPluginManager().disablePlugin(plugin.getBootstrap().getLoader());
        } else {
            plugin.getLogger().warn(String.format("Attempted to disable plugin '%s', but it was already disabled.", plugin.getBootstrap().getLoader().getName()));
        }
    }
}
