package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.extend.CommandWGRP;
import net.ritasister.wgrp.handler.abstracts.AbstractHandler;
import org.bukkit.Bukkit;

public class CommandHandler extends AbstractHandler<Void> {

    private final WorldGuardRegionProtect wgRegionProtect;

    public CommandHandler(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect=wgRegionProtect;
    }

    @Override
    public void handle() {
        try {
            new CommandWGRP(wgRegionProtect);
            Bukkit.getLogger().info("All commands registered successfully!");
        } catch (NullPointerException e) {
            Bukkit.getLogger().severe("""
                    Command cannot be null.
                    Possible for reason:
                    - command not set in getCommand(ucl.cmd_name).
                    - command not set in UtilCommandList.
                    - command not set in plugin.yml.
                    """);
        }
    }
}
