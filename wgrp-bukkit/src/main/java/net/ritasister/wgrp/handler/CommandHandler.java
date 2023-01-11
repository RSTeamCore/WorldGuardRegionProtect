package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.extend.CommandCredits;
import net.ritasister.wgrp.command.extend.CommandHelp;
import net.ritasister.wgrp.command.extend.CommandManageRegion;
import net.ritasister.wgrp.command.extend.CommandReload;
import net.ritasister.wgrp.command.extend.CommandSpy;

public class CommandHandler extends AbstractHandler<Void> {

    private final WorldGuardRegionProtect wgRegionProtect;

    public CommandHandler(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect=wgRegionProtect;
    }

    @Override
    public void handle() {
        try {
            new CommandCredits(wgRegionProtect);
            new CommandHelp(wgRegionProtect);
            new CommandManageRegion(wgRegionProtect);
            new CommandReload(wgRegionProtect);
            new CommandSpy(wgRegionProtect);
            wgRegionProtect.getLogger().info("All commands registered successfully!");
        } catch (NullPointerException e) {
            wgRegionProtect.getLogger().error("""
                    Command cannot be null.
                    Possible for reason:
                    - command not set in getCommand(ucl.cmd_name).
                    - command not set in UtilCommandList.
                    - command not set in plugin.yml.
                    """);
        }
    }
}
