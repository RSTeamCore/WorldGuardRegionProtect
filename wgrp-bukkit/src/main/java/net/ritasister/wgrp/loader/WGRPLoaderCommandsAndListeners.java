package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.handler.AbstractHandler;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import org.bukkit.plugin.PluginManager;

public class WGRPLoaderCommandsAndListeners extends LoaderCommandsAndListenersImpl<WorldGuardRegionProtect> {

    @Override
    public void loadCommands(WorldGuardRegionProtect worldGuardRegionProtect) {
        AbstractHandler<Void> registerCommands = new CommandHandler(worldGuardRegionProtect);
        registerCommands.handle();
    }

    @Override
    public void loadListeners(WorldGuardRegionProtect worldGuardRegionProtect) {
        AbstractHandler<PluginManager> registerListeners = new ListenerHandler(worldGuardRegionProtect);
        registerListeners.handle(worldGuardRegionProtect.getWgrpContainer().getPluginManager());
    }

}
