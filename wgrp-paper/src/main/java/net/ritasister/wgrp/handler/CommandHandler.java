package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.command.extend.CommandWGRP;

public class CommandHandler implements wgrp.handler.Handler<Void> {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public CommandHandler(WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public void handle() {
        new CommandWGRP(wgrpBukkitPlugin);
        log.info("All commands registered successfully!");
    }
}
