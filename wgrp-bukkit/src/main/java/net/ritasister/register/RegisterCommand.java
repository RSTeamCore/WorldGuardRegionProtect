package net.ritasister.register;

import net.ritasister.command.CommandWGRP;
import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.util.UtilCommandList;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class RegisterCommand {

	public static void RegisterCommands() {
		try{
			WorldGuardRegionProtect.getInstance().getCommand(UtilCommandList.WGRP.toString()).setExecutor(new CommandWGRP());
			RSLogger.info("&2All commands load successfully!");
		}catch(NullPointerException e){
			RSLogger.err("&cCommand cannot be &4null.");
			RSLogger.err("&cPossible for reason:");
			RSLogger.err("&c- command not set in &4getCommand(ucl.cmd_name).");
			RSLogger.err("&c- command not set in &4UtilCommandList.");
			RSLogger.err("&c- command not set in &4plugin.yml.");
		}
	}
}