package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.command.extend.CommandWGRP;

/**
 * Handler for register commands.
 */
public class CommandHandler implements Handler<Void> {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public CommandHandler(WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public void handle() {
        new CommandWGRP(wgrpBukkitPlugin);
        wgrpBukkitPlugin.getPluginLogger().info("All commands registered successfully!");
    }
}
