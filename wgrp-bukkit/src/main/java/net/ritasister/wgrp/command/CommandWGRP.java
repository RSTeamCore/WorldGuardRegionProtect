package net.ritasister.wgrp.command;

import net.ritasister.wgrp.rslibs.api.CmdExecutor;
import net.ritasister.wgrp.rslibs.permissions.IUtilPermissions;
import net.ritasister.wgrp.util.UtilCommandList;
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
	private final List<String> subCommand = List.of("reload", "help", "about", "spy");

	public CommandWGRP(WorldGuardRegionProtect wgRegionProtect) {
		super(UtilCommandList.WGRP.toString());
		this.wgRegionProtect=wgRegionProtect;
	}

	@Override
	protected void run(CommandSender sender, Command cmd, String[] args) {
		final boolean sp = sender instanceof Player;
		FileConfiguration configPatch = wgRegionProtect.getUtilLoadConfig().config;
		FileConfiguration getConfig = wgRegionProtect.getWgrpBukkitPlugin().getConfig();
		if(sp && wgRegionProtect.getRsApi().isSenderCommandsPermission(sender, cmd, IUtilPermissions.COMMAND_WGRP,
                wgRegionProtect.utilConfigMessage.noPerm))return; {}
		if(args.length > 0) {
			runCommand(sender, cmd, args, configPatch, getConfig);
		}else{
			sender.sendMessage(wgRegionProtect.utilConfigMessage.wgrpUseHelp);
		}
	}

	private void runCommand(@NotNull CommandSender sender, Command cmd, String @NotNull [] args, FileConfiguration configPatch, FileConfiguration getConfig) {
		final boolean sp = sender instanceof Player;
		if(args[0].equalsIgnoreCase("reload")) {
			wgRegionProtect.getUtilLoadConfig().getConfig().reload();
			sender.sendMessage(wgRegionProtect.getChatApi().getColorCode(wgRegionProtect.getUtilConfigMessage().configReloaded));
		}
		if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(wgRegionProtect.getChatApi().getColorCode("""
									sub-commands:
									/wgrp about
									/wgrp spy - enable or disable spying for log who interact with region.
									/wgrp add protect|protect-allow|only-break-allow <region name>
									"""));
		} else if(args[0].equalsIgnoreCase("about")) {
			sender.sendMessage(wgRegionProtect.getChatApi().getColorCode("""
									&b=======================================================================
									&aHi! If you need help from this plugin, you can contact with me on:
									&ehttps://www.spigotmc.org/resources/wgRegionProtect-1-12.81333/
									&ehttp://rubukkit.org/threads/sec-wgRegionProtect-0-7-0-pre2-dop-zaschita-dlja-regionov-wg-1-13-1-17.171324/page-4#post-1678435
									&b=======================================================================
									&aBut if you find any error or you want to send me any idea for this plugin&b,
									&aso you can create issues on github:&e https://github.com/RSTeamCore/WorldGuardRegionProtect
									&6your welcome!
									"""));
		} else if(sp && wgRegionProtect.getRsApi().isSenderCommandsPermission((Player) sender, cmd, IUtilPermissions.SPY_INSPECT_ADMIN_COMMAND,
                wgRegionProtect.utilConfigMessage.noPerm))return; {
			if(args[0].equalsIgnoreCase("spy")) {
				@NotNull UUID uniqueId = Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getUniqueId();
				if (wgRegionProtect.spyLog.contains(uniqueId)) {
					wgRegionProtect.spyLog.remove(uniqueId);
					sender.sendMessage("You are disable spy logging!");
				} else {
					wgRegionProtect.spyLog.add(uniqueId);
					sender.sendMessage("You are enable spy logging!");
				}
			}
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Player player, Command cmd, String label, String @NotNull [] args) {
		if (args.length == 1 && wgRegionProtect.getRsApi().isSenderCommandsPermissionOnTab(sender, IUtilPermissions.COMMAND_WGRP)) {
			return wgRegionProtect.getRsApi().isSenderCommandsPermissionOnTab(sender, IUtilPermissions.COMMAND_WGRP) && sender instanceof Player ?
					StringUtil.copyPartialMatches(args[0], subCommand, new ArrayList<>()) : new ArrayList<>();
		}else{
			return Collections.emptyList();
		}
	}
}