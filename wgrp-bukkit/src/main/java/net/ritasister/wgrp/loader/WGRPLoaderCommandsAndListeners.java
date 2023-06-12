package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.Handler;
import net.ritasister.wgrp.handler.ListenerHandler;
import org.bukkit.plugin.PluginManager;

public class WGRPLoaderCommandsAndListeners extends LoadHandlersImpl<WorldGuardRegionProtect> {

    @Override
    public void loadHandlers(WorldGuardRegionProtect wgRegionProtect) {
        Handler<Void> registerCommands = new CommandHandler(wgRegionProtect);
        registerCommands.handle();

        Handler<PluginManager> registerListeners = new ListenerHandler(wgRegionProtect);
        registerListeners.handle(wgRegionProtect.getWgrpContainer().getPluginManager());
    }

}
