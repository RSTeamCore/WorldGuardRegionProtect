package net.ritasister.command;

import java.util.*;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.StringUtil;

import net.ritasister.rslibs.api.CmdExecutor;
import net.ritasister.rslibs.api.RSApi;
import net.ritasister.util.UtilCommandList;
import net.ritasister.util.UtilPermissions;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class CommandReload extends CmdExecutor
{
	
	private final List<String> subCommand = Arrays.asList("reload");
	
	public CommandReload(UtilCommandList ucl) 
	{
		super(ucl.serverregionprotect);
	}

	@Override
	protected void run(CommandSender sender, Command cmd, String[] args) throws Exception
	{
		final boolean sp = sender instanceof Player;
		if(sp && !RSApi.isAuthCommandsPermission((Player)sender, cmd, UtilPermissions.reload_cfg,	
				WorldGuardRegionProtect.utilConfigMessage.noPerm)){return;}else{
			if (args.length == 1)
			{			
				if(args[0].equalsIgnoreCase("reload"))
				{
					WorldGuardRegionProtect.utilConfig = new UtilConfig();
					sender.sendMessage(WorldGuardRegionProtect.utilConfigMessage.configReloaded);
				}
			}
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Player player, Command cmd, String label, String[] args)
	{
		if (args.length == 1 && RSApi.isAuthCommandsPermissionsOnTab(sender, UtilPermissions.reload_cfg)) 
		{
			return args.length == 1 && RSApi.isAuthCommandsPermissionsOnTab(sender, UtilPermissions.reload_cfg) && sender instanceof Player ? 
						StringUtil.copyPartialMatches(args[0], subCommand, new ArrayList<>()) : new ArrayList<>();
		}else{
			return Collections.emptyList();
		}
	}
}