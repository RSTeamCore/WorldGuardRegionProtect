package net.ritasister.wgrp.handler;

import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.extend.CommandWGRP;

@Slf4j
public class CommandHandler implements Handler<Void> {

    private final WorldGuardRegionProtect wgRegionProtect;

    public CommandHandler(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect=wgRegionProtect;
    }

    @Override
    public void handle() {
        new CommandWGRP(wgRegionProtect);
        log.info("All commands registered successfully!");
    }
}
