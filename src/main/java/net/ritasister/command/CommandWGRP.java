package net.ritasister.command;

import java.util.*;

import net.ritasister.rslibs.api.ChatApi;
import net.ritasister.util.config.UtilConfigMessage;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.util.StringUtil;

import net.ritasister.rslibs.api.CmdExecutor;
import net.ritasister.rslibs.api.RSApi;
import net.ritasister.util.UtilCommandList;
import net.ritasister.util.IUtilPermissions;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class CommandWGRP extends CmdExecutor {
	private final List<String> subCommand = List.of("reload, admin, add, remove, spycmd");

	public CommandWGRP() {super(UtilCommandList.worldGuardRegionProtect);}

	@Override
	protected void run(CommandSender sender, Command cmd, String[] args) {
		final boolean sp = sender instanceof Player;
		UtilConfig configPatch = WorldGuardRegionProtect.utilConfig;
		FileConfiguration getConfig = WorldGuardRegionProtect.instance.getConfig();
		if(sp && !RSApi.isAuthCommandsPermission((Player)sender, cmd, IUtilPermissions.commandWGRP,
				WorldGuardRegionProtect.utilConfigMessage.noPerm)) {return;}{
			if(args.length <= 1) {
				runCommand(sender, cmd, args, configPatch, getConfig);
			}else{
				sender.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpUseHelp);
			}
		}
	}

	private void runCommand(CommandSender sender, Command cmd, String[] args, UtilConfig configPatch, FileConfiguration getConfig) {
		if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(ChatApi.getDoubleTabSpaceWithColor("sub-commands:\n/wgrp about\n/wgrp add protect|protect-allow|only-break-allow <region name>"));
		}
		if(args[0].equalsIgnoreCase("about")) {
			sender.sendMessage(ChatApi.getDoubleTabSpaceWithColor("&b======================================================================================================================================\n&aHi! If you need help from this plugin, you can contact with me on: \n&ehttps://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-17.81321/\n&ehttp://rubukkit.org/threads/sec-worldguardregionprotect-0-7-0-pre2-dop-zaschita-dlja-regionov-wg-1-13-1-17.171324/page-4#post-1678435\n&ehttps://spigotmc.ru/threads/worldguardregionprotect-dopolnitelnaja-zaschita-dlja-regionov-wg-1-13-1-17.3411/#post-30581\n&b======================================================================================================================================\n&aBut if you find any error or you want to send me any idea for this plugin&b, \n&aso you can create issues on github: &ehttps://github.com/RitaSister/WorldGuardRegionProtect/issues\n&6your welcome!"));
		}
		if(args[0].equalsIgnoreCase("reload")) {
			WorldGuardRegionProtect.utilConfig = new UtilConfig();
			sender.sendMessage(WorldGuardRegionProtect.utilConfigMessage.configReloaded);
		}
		if(args[0].equalsIgnoreCase("reloadmsg")) {
			WorldGuardRegionProtect.utilConfigMessage = new UtilConfigMessage();
			sender.sendMessage(WorldGuardRegionProtect.utilConfigMessage.configMsgReloaded);
		}else if(args.length == 3) {
			if(args[0].equalsIgnoreCase("add")) {
				if(args[1].equalsIgnoreCase("protect")) {
					configPatch.regionProtect.add(args[2]);
					WorldGuardRegionProtect.instance.saveDefaultConfig();
					sender.sendMessage("You added <region name> in to protect!".replace("<region name>", args[2]));
				}
			}else{sender.sendMessage("You need type arguments like: /wgrp add protect|protect-allow|only-break-allow <region name>");}
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Player player, Command cmd, String label, String[] args) {
		if (args.length == 1 && RSApi.isAuthCommandsPermissionsOnTab(sender, IUtilPermissions.commandWGRP)) {
			return RSApi.isAuthCommandsPermissionsOnTab(sender, IUtilPermissions.commandWGRP) && sender instanceof Player ?
					StringUtil.copyPartialMatches(args[0], subCommand, new ArrayList<>()) : new ArrayList<>();
		}else{
			return Collections.emptyList();
		}
	}
}