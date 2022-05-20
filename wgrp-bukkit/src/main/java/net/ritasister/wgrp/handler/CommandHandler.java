package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.command.CommandWGRP;
import net.ritasister.wgrp.rslibs.api.interfaces.ICommandHandler;
import net.ritasister.wgrp.util.UtilCommandList;
import net.ritasister.wgrp.WorldGuardRegionProtect;

import java.util.Objects;

public record CommandHandler(WorldGuardRegionProtect wgRegionProtect) implements ICommandHandler {

	@Override
	public void commandHandler() {
		try{
			Objects.requireNonNull(wgRegionProtect.getWgrpBukkitPlugin().getCommand(UtilCommandList.WGRP.getCommand()))
					.setExecutor(new CommandWGRP(wgRegionProtect));
			wgRegionProtect.getRsApi().getLogger().info("&2All commands registered successfully!");
		}catch(NullPointerException e) {
			wgRegionProtect.getRsApi().getLogger().error("&cCommand cannot be &4null.");
			wgRegionProtect.getRsApi().getLogger().error("&cPossible for reason:");
			wgRegionProtect.getRsApi().getLogger().error("&c- command not set in &4getCommand(ucl.cmd_name).");
			wgRegionProtect.getRsApi().getLogger().error("&c- command not set in &4UtilCommandList.");
			wgRegionProtect.getRsApi().getLogger().error("&c- command not set in &4plugin.yml.");
		}
	}
}