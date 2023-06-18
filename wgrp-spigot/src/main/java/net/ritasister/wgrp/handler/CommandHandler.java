package net.ritasister.wgrp.handler;

import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.command.extend.CommandWGRP;

@Slf4j
public class CommandHandler implements Handler<Void> {

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
