package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.api.handler.LoadHandlers;
import net.ritasister.wgrp.handler.CommandHandler;

public final class WGRPLoaderCommands implements LoadHandlers<WorldGuardRegionProtectPaperPlugin> {

    @Override
    public void loadHandler(WorldGuardRegionProtectPaperPlugin wgrpBukkitPlugin) {
        final Handler<Void> registerCommands = new CommandHandler(wgrpBukkitPlugin);
        registerCommands.handle();
    }

}
