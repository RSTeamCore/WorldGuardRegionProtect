package net.ritasister.wgrp.loader.plugin;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class LoadWorldGuard {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public LoadWorldGuard(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    public void loadPlugin() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin != null && plugin.isEnabled()) {
            wgrpPlugin.getLogger().info(String.format("Plugin '%s' loaded successfully!", plugin.getName()));
        } else {
            wgrpPlugin.getLogger().warn("WorldGuard plugin is not installed or not enabled. Features requiring WorldGuard will not function.");
        }
    }

}
