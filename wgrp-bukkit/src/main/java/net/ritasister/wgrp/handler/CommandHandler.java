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
        try {
            new CommandWGRP(wgRegionProtect);
            WGRPContainer.getLogger().info("All commands registered successfully!");
        } catch (NullPointerException e) {
            WGRPContainer.getLogger().error("""
                    Command cannot be null.
                    Possible for reason:
                    - command not set in getCommand(ucl.cmd_name).
                    - command not set in UtilCommandList.
                    - command not set in plugin.yml.
                    """);
        }
    }
}
