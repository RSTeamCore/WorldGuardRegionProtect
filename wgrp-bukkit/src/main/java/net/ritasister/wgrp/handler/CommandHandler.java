package net.ritasister.wgrp.handler;

import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.extend.CommandWGRP;

public record CommandHandler(WorldGuardRegionProtect wgRegionProtect) {

	public void commandHandler() {
		try{
			new CommandWGRP(wgRegionProtect);
			wgRegionProtect.getLogger().info(Component.text("&2All commands registered successfully!"));
		}catch(NullPointerException e) {
			wgRegionProtect.getLogger().error(Component.text("""
						&cCommand cannot be &4null.
						&cPossible for reason:
						&c- command not set in &4getCommand(ucl.cmd_name).
						&c- command not set in &4UtilCommandList.
						&c- command not set in &4plugin.yml.
						"""));
		}
	}
}