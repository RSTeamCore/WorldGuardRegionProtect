package wgrp.loader;

import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.handler.Handler;
import wgrp.handler.ListenerHandler;

public class WGRPLoaderListeners implements LoadHandlers<WorldGuardRegionProtectBukkitPlugin> {

    @Override
    public void loadHandler(@NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        Handler<PluginManager> registerListeners = new ListenerHandler(wgrpBukkitPlugin);
        registerListeners.handle(wgrpBukkitPlugin.getWgrpBukkitBase().getServer().getPluginManager());
    }

}
