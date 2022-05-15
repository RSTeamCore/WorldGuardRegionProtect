package net.ritasister.rslibs.api;

import net.ritasister.rslibs.chat.api.ChatApi;
import net.ritasister.rslibs.permissions.IUtilPermissions;
import net.ritasister.util.Time;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface RSApi {

	/**
	 * Check if a player has permissions for commands.
	 *
	 * @param sender  Who send this command.
	 * @param cmd     Name command.
	 * @param perm    Permission to check.
	 * @param message return custom message for sender.
	 * @return isSenderCommandsPermission if Sender can use commands.
	 */
	static boolean isSenderCommandsPermission(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String perm, String message) {
		if (!sender.hasPermission(perm) || !sender.isPermissionSet(perm)) {
			if (message != null) {
				sender.sendMessage(ChatApi.getColorCode(message));
			}
			return false;
		}
		return true;
	}

	/**
	 * Check if a player has permission for use TAB.
	 *
	 * @param sender Who send this command.
	 * @param perm   Permission to check.
	 * @return isAuthCommandsPermissionsOnTab if Sender can use TAB.
	 */
	static boolean isSenderCommandsPermissionOnTab(@NotNull CommandSender sender, @NotNull String perm) {
		return sender.hasPermission(perm) && sender.isPermissionSet(perm);
	}

	/**
	 * Check if a player has permission for use Listener.
	 *
	 * @param sender  Who send this command.
	 * @param perm    Permission to check.
	 * @param message return custom message for sender.
	 * @return isAuthListenerPermission if Sender can use Events.
	 */
	static boolean isSenderListenerPermission(@NotNull CommandSender sender, @NotNull String perm, String message) {
		if (!sender.hasPermission(perm) || !sender.isPermissionSet(perm)) {
			if (message != null) {
				sender.sendMessage(ChatApi.getColorCode(message));
			}
			return false;
		}
		return true;
	}

	/**
	 * Send notification to admin.
	 *
	 * @param player        return player object.
	 * @param playerName    return nickname player.
	 * @param senderCommand return name command if player attempt to use in region.
	 * @param regionName    return region name, if player attempts to use command in region.
	 */
	static void notify(Player player, String playerName, String senderCommand, String regionName) {
		if (WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdmin
				&& RSApi.isSenderListenerPermission(player, IUtilPermissions.regionProtectNotifyAdmin, null)) {
			for (String cmd : WorldGuardRegionProtect.utilConfig.spyCommand) {
				if (cmd.equalsIgnoreCase(senderCommand.toLowerCase())
						&& WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdminPlaySoundEnable) {
					player.playSound(player.getLocation(), WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdminPlaySound, 1, 1);
					player.sendMessage(WorldGuardRegionProtect.utilConfigMessage.sendAdminInfoIfUsedCommandInRG
							.replace("<player>", playerName)
							.replace("<cmd>", cmd)
							.replace("<rg>", regionName));
				}
			}
		}
	}

	/**
	 * Send notify to admin.
	 *
	 * @param playerName    return player object.
	 * @param senderCommand return name command if player attempt to use in region.
	 * @param regionName    return region name, if player attempts to use command in region.
	 */
	static void notify(String playerName, String senderCommand, String regionName) {
		if (WorldGuardRegionProtect.utilConfig.spyCommandNotifyConsole) {
			for (String cmd : WorldGuardRegionProtect.utilConfig.spyCommand) {
				if (cmd.equalsIgnoreCase(senderCommand.toLowerCase())) {
					Bukkit.getConsoleSender().sendMessage(WorldGuardRegionProtect.utilConfigMessage.sendAdminInfoIfUsedCommandInRG
							.replace("<player>", playerName)
							.replace("<cmd>", cmd)
							.replace("<region>", regionName));
				}
			}
		}
	}

	/**
	 * Send notification if player attempts to interact with region from WorldGuard.
	 *
	 * @param admin       return message for admin who destroys region.
	 * @param suspect     return object player for method.
	 * @param time        return time if a region is broken of player.
	 * @param suspectName return player name who's interacting with region.
	 * @param action	  return type of Actions.
	 * @param regionName  return region name.
	 * @param x           return X position of block.
	 * @param y           return Y position of block.
	 * @param z           return Z position of block.
	 * @param world       return world position of block.
	 */
	static void notifyIfActionInRegion(Player admin, Player suspect, String time, String suspectName, Action action, String regionName, double x, double y, double z, String world) {
		if (RSApi.isSenderListenerPermission(suspect, IUtilPermissions.spyInspectForSuspect, null)
				&& WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdmin) {
				admin.sendMessage(WorldGuardRegionProtect.utilConfigMessage.sendAdminInfoIfActionInRegion
						.replace("<time>", time).replace("<player>", suspectName)
					.replace("<action>", action.toString())
					.replace("<region>", regionName)
					.replace("<x>", String.valueOf(x))
					.replace("<y>", String.valueOf(y))
					.replace("<z>", String.valueOf(z))
					.replace("<world>", world));
		}
	}
	/**
	 * Initializes all used NMS classes, constructors, fields and methods.
	 * Returns {@code true} if everything went successfully and version is marked as compatible,
	 * {@code false} if anything went wrong or version is not marked as compatible.
	 * @return    {@code true} if server version is compatible, {@code false} if not
	 */
	static boolean isVersionSupported(){
		List<String> supportedVersions = Arrays.asList(
				"v1_18_R1", "v1_18_R2");
		String serverPackage = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			long time = System.currentTimeMillis();
			if (supportedVersions.contains(serverPackage)) {
				RSLogger.info("&7Loaded NMS hook in " + (System.currentTimeMillis()-time) + "ms");
				return true;
			} else {
				RSLogger.info("&cNo compatibility issue was found, but this plugin version does not claim to support your server package (" + serverPackage + "). Disabling just to stay safe.");
			}
		} catch (Exception ex) {
			if (supportedVersions.contains(serverPackage)) {
				RSLogger.err("&cYour server version is marked as compatible, but a compatibility issue was found. Please report the error below (include your server version & fork too)");
			} else {
				RSLogger.err("&cYour server version is completely unsupported. Disabling.");
			}
		}
		return false;
	}

	static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy - HH:mm");
		Date resultDate = new Date(System.currentTimeMillis());
		return sdf.format(resultDate);
	}

	static String getProtectRegionName(Location loc) {
		return RSRegion.getProtectRegion(loc);
	}
}