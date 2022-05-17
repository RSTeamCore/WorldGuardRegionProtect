package net.ritasister.register;

import net.ritasister.command.CommandWGRP;
import net.ritasister.rslibs.api.interfaces.IRegisterCommand;
import net.ritasister.util.UtilCommandList;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class RegisterCommand implements IRegisterCommand {

	@Override
	public void registerCommand() {
		try{
			WorldGuardRegionProtect worldGuardRegionProtect = WorldGuardRegionProtect.getPlugin(WorldGuardRegionProtect.class);
			worldGuardRegionProtect.getCommand(UtilCommandList.WGRP.getCommand())
					.setExecutor(new CommandWGRP(worldGuardRegionProtect));
			WorldGuardRegionProtect.getInstance().getRsApi().getLogger().info("&2All commands registered successfully!");
		}catch(NullPointerException e){
			WorldGuardRegionProtect.getInstance().getRsApi().getLogger().error("&cCommand cannot be &4null.");
			WorldGuardRegionProtect.getInstance().getRsApi().getLogger().error("&cPossible for reason:");
			WorldGuardRegionProtect.getInstance().getRsApi().getLogger().error("&c- command not set in &4getCommand(ucl.cmd_name).");
			WorldGuardRegionProtect.getInstance().getRsApi().getLogger().error("&c- command not set in &4UtilCommandList.");
			WorldGuardRegionProtect.getInstance().getRsApi().getLogger().error("&c- command not set in &4plugin.yml.");
		}
	}
}