package net.ritasister.command;

import net.ritasister.rslibs.api.CmdExecutor;
import net.ritasister.rslibs.permissions.IUtilPermissions;
import net.ritasister.util.UtilCommandList;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class CommandWGRP extends CmdExecutor {

	private final WorldGuardRegionProtect wgRegionProtect;
	private final List<String> subCommand = List.of("help", "about", "spy");

	public CommandWGRP(WorldGuardRegionProtect wgRegionProtect) {
		super(UtilCommandList.WGRP.toString());
		this.wgRegionProtect=wgRegionProtect;
	}

	@Override
	protected void run(CommandSender sender, Command cmd, String[] args) {
		final boolean sp = sender instanceof Player;
		FileConfiguration configPatch = wgRegionProtect.getUtilLoadConfig().config;
		FileConfiguration getConfig = WorldGuardRegionProtect.getInstance().getConfig();
		if(sp && wgRegionProtect.getRsApi().isSenderCommandsPermission((Player) sender, cmd, IUtilPermissions.COMMAND_WGRP,
                wgRegionProtect.utilConfigMessage.noPerm))return; {}
		if(args.length > 0) {
			runCommand(sender, cmd, args, configPatch, getConfig);
		}else{
			sender.sendMessage(WorldGuardRegionProtect.getInstance().utilConfigMessage.wgrpUseHelp);
		}
	}

	private void runCommand(CommandSender sender, Command cmd, String[] args, FileConfiguration configPatch, FileConfiguration getConfig) {
		final boolean sp = sender instanceof Player;
		if (args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(wgRegionProtect.getChatApi().getDoubleTabSpaceWithColor("sub-commands:\n/wgrp about\n/wgrp spy - enable or disable spying for log who interact with region.\n/wgrp add protect|protect-allow|only-break-allow <region name>"));
		} else if (args[0].equalsIgnoreCase("about")) {
			sender.sendMessage(wgRegionProtect.getChatApi().getDoubleTabSpaceWithColor("&b===================================================\n&aHi! If you need help from this plugin, you can contact with me on: \n&ehttps://www.spigotmc.org/resources/wgRegionProtect-1-12.81333/\n&ehttp://rubukkit.org/threads/sec-wgRegionProtect-0-7-0-pre2-dop-zaschita-dlja-regionov-wg-1-13-1-17.171324/page-4#post-1678435\n&b===================================================\n&aBut if you find any error or you want to send me any idea for this plugin&b, \n&aso you can create issues on github:&e https://github.com/RitaSister/WorldGuardRegionProtect/issues\n&6your welcome!"));
		} else if(sp && wgRegionProtect.getRsApi().isSenderCommandsPermission((Player) sender, cmd, IUtilPermissions.SPY_INSPECT_ADMIN_COMMAND,
                wgRegionProtect.utilConfigMessage.noPerm))return; {
			if(args[0].equalsIgnoreCase("spy")) {
				@NotNull UUID uniqueId = Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getUniqueId();
				if (wgRegionProtect.spyLog.contains(uniqueId)) {
					wgRegionProtect.spyLog.remove(uniqueId);
					sender.sendMessage("You disable spy logging!");
				} else {
					wgRegionProtect.spyLog.add(uniqueId);
					sender.sendMessage("You enable spy logging!");
				}
			}
		}
		/*if (args.length == 2 && args[0].equalsIgnoreCase("add") && sender.hasPermission("opsrg.admin")) {
			wgRegionProtect.functions.add(List.of(args[1]), sender, false);
		}
		if (args.length == 2 && args[0].equalsIgnoreCase("del") && sender.hasPermission("opsrg.admin")) {
			wgRegionProtect.functions.rem(List.of(args[1]), sender, false);
		}
		if (args.length == 2 && args[0].equalsIgnoreCase("addmine") && sender.hasPermission("opsrg.admin")) {
			wgRegionProtect.functions.add(List.of(args[1]), sender, true);
		}
		if (args.length == 2 && args[0].equalsIgnoreCase("delmine") && sender.hasPermission("opsrg.admin")) {
			wgRegionProtect.functions.rem(List.of(args[1]), sender, true);
		}
		if (args.length <= 2 && args[0].equalsIgnoreCase("list") && sender.hasPermission("opsrg.admin")) {
			wgRegionProtect.getRsRegion().paginate(sender, args, false);
			return;
		}
		if (args.length <= 2 && args[0].equalsIgnoreCase("listmine") && sender.hasPermission("opsrg.admin")) {
			wgRegionProtect.getRsRegion().paginate(sender, args, true);
		}*/
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Player player, Command cmd, String label, String[] args) {
		if (args.length == 1 && wgRegionProtect.getRsApi().isSenderCommandsPermissionOnTab(sender, IUtilPermissions.COMMAND_WGRP)) {
			return wgRegionProtect.getRsApi().isSenderCommandsPermissionOnTab(sender, IUtilPermissions.COMMAND_WGRP) && sender instanceof Player ?
					StringUtil.copyPartialMatches(args[0], subCommand, new ArrayList<>()) : new ArrayList<>();
		}else{
			return Collections.emptyList();
		}
	}
}