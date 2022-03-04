package net.ritasister.command;

import java.util.*;

import net.ritasister.rslibs.api.ChatApi;
import net.ritasister.util.UtilCommandList;
import net.ritasister.util.config.UtilConfigMessage;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.util.StringUtil;

import net.ritasister.rslibs.api.CmdExecutor;
import net.ritasister.rslibs.api.RSApi;
import net.ritasister.util.IUtilPermissions;

public class CommandWGRP extends CmdExecutor {

	private final UtilConfigMessage utilConfigMessage;

	private final List<String> subCommand = List.of("reload, admin, add, remove, spycmd");

	public CommandWGRP(UtilConfigMessage utilConfigMessage) {
		super(UtilCommandList.worldGuardRegionProtect);
		this.utilConfigMessage = utilConfigMessage;
	}

	@Override
	protected void run(CommandSender sender, Command cmd, String[] args) {
		final boolean sp = sender instanceof Player;
		if(sp && !RSApi.isAuthCommandsPermission((Player)sender, cmd, IUtilPermissions.commandWGRP,
				utilConfigMessage.noPerm())) {return;}{
			if(args.length > 0) {
				runCommand(sender, cmd, args);
			}/*else{
				sender.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpUseHelp);
			}*/
		}
	}

	private void runCommand(CommandSender sender, Command cmd, String[] args) {
		if(args.length == 1 && args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(ChatApi.getDoubleTabSpaceWithColor("sub-commands:\n/wgrp about\n/wgrp add protect|protect-allow|only-break-allow <region name>"));
		}else if(args.length == 1 && args[0].equalsIgnoreCase("about")) {
			sender.sendMessage(ChatApi.getDoubleTabSpaceWithColor("&b======================================================================================================================================\n&aHi! If you need help from this plugin, you can contact with me on: \n&ehttps://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-17.81321/\n&ehttp://rubukkit.org/threads/sec-worldguardregionprotect-0-7-0-pre2-dop-zaschita-dlja-regionov-wg-1-13-1-17.171324/page-4#post-1678435\n&ehttps://spigotmc.ru/threads/worldguardregionprotect-dopolnitelnaja-zaschita-dlja-regionov-wg-1-13-1-17.3411/#post-30581\n&b======================================================================================================================================\n&aBut if you find any error or you want to send me any idea for this plugin&b, \n&aso you can create issues on github: &ehttps://github.com/RitaSister/WorldGuardRegionProtect/issues\n&6your welcome!"));
		}else if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			//WorldGuardRegionProtect.utilConfig = new UtilConfig();
			sender.sendMessage(utilConfigMessage.getConfigReloaded());
		}else if(args.length == 1 && args[0].equalsIgnoreCase("reloadmsg")) {
			//WorldGuardRegionProtect.utilConfigMessage = new UtilConfigMessage();
			sender.sendMessage(utilConfigMessage.getConfigMsgReloaded());
		}else if(args.length == 2 && args[0].equalsIgnoreCase("add")) {
			//WorldGuardRegionProtect.utilConfig.regionProtect.add(args[0]);
		}else if(args.length == 2 && args[0].equalsIgnoreCase("remove")) {
			//WorldGuardRegionProtect.utilConfig.regionProtect.remove(args[0]);
		}else{sender.sendMessage("You need type arguments like: /wgrp add protect|protect-allow|only-break-allow <region name>");}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Player player, Command cmd, String label, String[] args) {
		if (args.length == 1 && RSApi.isAuthCommandsPermissionsOnTab(sender, IUtilPermissions.commandWGRP)) {
			return RSApi.isAuthCommandsPermissionsOnTab(sender, IUtilPermissions.commandWGRP) && sender instanceof Player ?
					StringUtil.copyPartialMatches(args[0], subCommand, new ArrayList<>()) : new ArrayList<>();
		}else{return Collections.emptyList();}
	}
}