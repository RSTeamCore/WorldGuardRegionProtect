package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.Handler;

public class WGRPLoaderCommands implements LoadHandlers<WorldGuardRegionProtectBukkitPlugin> {

    @Override
    public void loadHandler(WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        Handler<Void> registerCommands = new CommandHandler(wgrpBukkitPlugin);
        registerCommands.handle();
    }

}
