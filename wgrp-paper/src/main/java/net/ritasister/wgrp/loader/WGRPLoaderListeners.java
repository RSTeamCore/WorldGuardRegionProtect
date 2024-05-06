package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.handler.ListenerHandler;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import wgrp.handler.Handler;

public class WGRPLoaderListeners implements LoadHandlers<WorldGuardRegionProtectBukkitPlugin> {

    @Override
    public void loadHandler(@NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        Handler<PluginManager> registerListeners = new ListenerHandler(wgrpBukkitPlugin);
        registerListeners.handle(wgrpBukkitPlugin.getWgrpBukkitBase().getServer().getPluginManager());
    }

}
