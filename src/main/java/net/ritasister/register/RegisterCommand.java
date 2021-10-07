package net.ritasister.register;

import org.bukkit.Bukkit;

import net.ritasister.command.CommandReload;
import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.util.UtilCommandList;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class RegisterCommand 
{
	public static void RegisterCommands(UtilCommandList ucl)
	{
		try 
		{
			WorldGuardRegionProtect.instance.getCommand(ucl.serverregionprotect).setExecutor(new CommandReload(ucl));
			RSLogger.info("&2All commands load successfully!");
		}catch(NullPointerException e){
			RSLogger.err("&cCommand cannout be &4null."); 
			RSLogger.err("&cPossible for reason:");
			RSLogger.err("&c- command not set in &4getCommand(ucl.cmd_name).");
			RSLogger.err("&c- command not set in &4UtilCommandList.");
			RSLogger.err("&c- command not set in &4plugin.yml.");
		}
	}
}