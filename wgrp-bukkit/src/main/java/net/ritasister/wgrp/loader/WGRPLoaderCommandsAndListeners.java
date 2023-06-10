package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.handler.AbstractHandler;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import org.bukkit.plugin.PluginManager;

public class WGRPLoaderCommandsAndListeners extends LoadHandlersImpl<WorldGuardRegionProtect> {

    @Override
    public void loadHandlers(WorldGuardRegionProtect wgRegionProtect) {
        AbstractHandler<Void> registerCommands = new CommandHandler(wgRegionProtect);
        registerCommands.handle();

        AbstractHandler<PluginManager> registerListeners = new ListenerHandler(wgRegionProtect);
        registerListeners.handle(wgRegionProtect.getWgrpContainer().getPluginManager());
    }

}
