package net.ritasister.command;

import java.util.*;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.StringUtil;

import net.ritasister.srp.SRPApi;
import net.ritasister.srp.ServerRegionProtect;
import net.ritasister.command.executor.SRPCommandExecutor;
import net.ritasister.util.UtilCommandList;
import net.ritasister.util.UtilPermissions;
import net.ritasister.util.config.UtilConfig;

public class CommandReload extends SRPCommandExecutor
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
		if(sp && !SRPApi.isAuthCommandsPermission((Player)sender, cmd, UtilPermissions.reload_cfg,	
				ServerRegionProtect.utilConfigMessage.noPerm)){return;}else{
			if (args.length == 1)
			{			
				if(args[0].equalsIgnoreCase("reload"))
				{
					ServerRegionProtect.utilConfig = new UtilConfig();
					sender.sendMessage(ServerRegionProtect.utilConfigMessage.configReloaded);
				}
			}
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Player player, Command cmd, String label, String[] args)
	{
		if (args.length == 1 && SRPApi.isAuthCommandsPermissionsOnTab(sender, UtilPermissions.reload_cfg)) 
		{
			return args.length == 1 && SRPApi.isAuthCommandsPermissionsOnTab(sender, UtilPermissions.reload_cfg) && sender instanceof Player ? 
						StringUtil.copyPartialMatches(args[0], subCommand, new ArrayList<>()) : new ArrayList<>();
		}else{
			return Collections.emptyList();
		}
	}
}