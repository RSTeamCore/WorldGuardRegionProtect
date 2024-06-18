package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.api.handler.LoadHandlers;
import net.ritasister.wgrp.handler.CommandHandler;

/**
 * Utility class for a load and registered all commands in CommandHandler.
 */
public class WGRPLoaderCommands implements LoadHandlers<WorldGuardRegionProtectBukkitPlugin> {

    @Override
    public void loadHandler(WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        final Handler<Void> registerCommands = new CommandHandler(wgrpBukkitPlugin);
        registerCommands.handle();
    }

}
