package net.ritasister.command;

import java.util.*;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.util.StringUtil;

import net.ritasister.rslibs.api.CmdExecutor;
import net.ritasister.rslibs.api.RSApi;
import net.ritasister.util.UtilCommandList;
import net.ritasister.util.IUtilPermissions;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class CommandWGRP extends CmdExecutor
{
	
	private final List<String> subCommand = List.of("reload, admin");
	
	public CommandWGRP(UtilCommandList ucl) {super(ucl.worldGuardRegionProtect);}

	@Override
	protected void run(CommandSender sender, Command cmd, String[] args) throws Exception
	{
		final boolean sp = sender instanceof Player;
		if(sp && !RSApi.isAuthCommandsPermission((Player)sender, cmd, IUtilPermissions.reload_cfg,
				WorldGuardRegionProtect.utilConfigMessage.noPerm)) {return;}
		{
			if (args.length == 1)
			{	
				if(args[0].equalsIgnoreCase("help"))
				{
					sender.sendMessage(
							"&b======================================================================================================================================"
							+ "\n&aHi Admin! If you need help from this plugin, you can contact with me on: "
							+ "\n&ehttps://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-17.81321/"
							+ "\n&ehttp://rubukkit.org/threads/sec-worldguardregionprotect-0-7-0-pre2-dop-zaschita-dlja-regionov-wg-1-13-1-17.171324/page-4#post-1678435"
							+ "\n&ehttps://spigotmc.ru/threads/worldguardregionprotect-dopolnitelnaja-zaschita-dlja-regionov-wg-1-13-1-17.3411/#post-30581"
							+ "\n&b======================================================================================================================================"
							+ "\n&aBut if you find any error or you want to send me any idea for this plugin&b, "
							+ "\n&aso you can create issues on github: &ehttps://github.com/RitaSister/WorldGuardRegionProtect/issues"
							+ "\n&6your welcome!"
							.replace("\\n", "\n").replace("&", "§"));
				}
				if(args[0].equalsIgnoreCase("reload"))
				{
					WorldGuardRegionProtect.utilConfig = new UtilConfig();
					sender.sendMessage(WorldGuardRegionProtect.utilConfigMessage.configReloaded);
				}
			}else{
				sender.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpUseHelp);
			}
		}
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Player player, Command cmd, String label, String[] args)
	{
		if (args.length == 1 && RSApi.isAuthCommandsPermissionsOnTab(sender, IUtilPermissions.reload_cfg)) 
		{
			return args.length == 1 && RSApi.isAuthCommandsPermissionsOnTab(sender, IUtilPermissions.reload_cfg) && sender instanceof Player ?
						StringUtil.copyPartialMatches(args[0], subCommand, new ArrayList<>()) : new ArrayList<>();
		}else{
			return Collections.emptyList();
		}
	}
}