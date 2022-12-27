package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.extend.CommandWGRP;

public record CommandHandler(WorldGuardRegionProtect wgRegionProtect) {

	public void commandHandler() {
		try{
			new CommandWGRP(wgRegionProtect);
			wgRegionProtect.getLogger().info("All commands registered successfully!");
		}catch(NullPointerException e) {
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
