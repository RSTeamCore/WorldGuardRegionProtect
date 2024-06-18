package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.api.handler.LoadHandlers;
import net.ritasister.wgrp.handler.ListenerHandler;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for a load and registered all listeners in ListenerHandler.
 */
public class WGRPLoaderListeners implements LoadHandlers<WorldGuardRegionProtectBukkitPlugin> {

    @Override
    public void loadHandler(@NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        final Handler<PluginManager> registerListeners = new ListenerHandler(wgrpBukkitPlugin);
        registerListeners.handle(wgrpBukkitPlugin.getWgrpBukkitBase().getServer().getPluginManager());
    }

}
