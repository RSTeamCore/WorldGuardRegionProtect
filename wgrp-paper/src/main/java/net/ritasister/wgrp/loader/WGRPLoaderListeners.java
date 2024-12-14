package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.api.handler.LoadHandlers;
import net.ritasister.wgrp.handler.ListenerHandler;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public final class WGRPLoaderListeners implements LoadHandlers<WorldGuardRegionProtectPaperPlugin> {

    @Override
    public void loadHandler(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        final Handler<PluginManager> registerListeners = new ListenerHandler(wgrpPlugin);
        registerListeners.handle(wgrpPlugin.getWgrpPaperBase().getServer().getPluginManager());
    }

}
