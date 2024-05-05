package wgrp.loader;

import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.handler.CommandHandler;
import wgrp.handler.Handler;

public class WGRPLoaderCommands implements LoadHandlers<WorldGuardRegionProtectBukkitPlugin> {

    @Override
    public void loadHandler(WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        Handler<Void> registerCommands = new CommandHandler(wgrpBukkitPlugin);
        registerCommands.handle();
    }

}
