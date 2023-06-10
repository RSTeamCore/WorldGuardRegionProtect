package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.extend.CommandWGRP;

public class CommandHandler extends AbstractHandler<Void> {

    private final WorldGuardRegionProtect wgRegionProtect;

    public CommandHandler(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect=wgRegionProtect;
    }

    @Override
    public void handle() {
        new CommandWGRP(wgRegionProtect);
        WGRPContainer.getLogger().info("All commands registered successfully!");
    }
}
