package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.command.extend.CommandWGRP;

/**
 * Handler for register commands.
 */
public class CommandHandler implements Handler<Void> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public CommandHandler(WorldGuardRegionProtectPaperPlugin wgrpPaperPlugin) {
        this.wgrpPlugin = wgrpPaperPlugin;
    }

    @Override
    public void handle() {
        new CommandWGRP(wgrpPlugin);
        wgrpPlugin.getLogger().info("All commands registered successfully!");
    }
}
