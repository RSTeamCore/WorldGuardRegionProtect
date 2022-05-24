package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.permissions.IUtilPermissions;
import net.ritasister.wgrp.util.config.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class RSApi {

	private final WorldGuardRegionProtect wgRegionProtect;

	public RSApi(WorldGuardRegionProtect worldGuardRegionProtect) {
		this.wgRegionProtect =worldGuardRegionProtect;
	}

	/**
	 * Check if a player has permissions for commands.
	 *
	 * @param sender  Who send this command.
	 * @param cmd     Name command.
	 * @param perm    Permission to check.
	 * @param message return custom message for sender.
	 * @return isSenderCommandsPermission if Sender can use commands.
	 */
	public boolean isSenderCommandsPermission(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull IUtilPermissions perm, String message) {
		if (!sender.hasPermission(perm.getPermissionName()) || !sender.isPermissionSet(perm.getPermissionName())) {
			if (message != null) {
				sender.sendMessage(wgRegionProtect.getChatApi().getColorCode(message));

			}
			return true;
		}
		return false;
	}

	/**
	 * Check if a player has permission for use TAB.
	 *
	 * @param sender Who send this command.
	 * @param perm   Permission to check.
	 * @return isAuthCommandsPermissionsOnTab if Sender can use TAB.
	 */
	public boolean isSenderCommandsPermissionOnTab(@NotNull CommandSender sender, @NotNull IUtilPermissions perm) {
		return sender.hasPermission(perm.getPermissionName()) && sender.isPermissionSet(perm.getPermissionName());
	}

	/**
	 * Check if a player has permission for use Listener.
	 *
	 * @param sender  Who send this command.
	 * @param perm    Permission to check.
	 * @param message return custom message for sender.
	 * @return isAuthListenerPermission if Sender can use Events.
	 */
	public boolean isSenderListenerPermission(@NotNull CommandSender sender, @NotNull IUtilPermissions perm, String message) {
		if (!sender.hasPermission(perm.getPermissionName()) || !sender.isPermissionSet(perm.getPermissionName())) {
			if (message != null) {
				sender.sendMessage(wgRegionProtect.getChatApi().getColorCode(message));
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
	public void notify(Player player, String playerName, String senderCommand, String regionName) {
		if (wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdmin()
				&& this.isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT_NOTIFY_ADMIN, null)) {
			for (String cmd : wgRegionProtect.getUtilConfig().getConfig().getSpyCommandList()) {
				if (cmd.equalsIgnoreCase(senderCommand.toLowerCase())
						&& wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdminPlaySoundEnable()) {
					player.playSound(player.getLocation(), wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdminPlaySound().toLowerCase(), 1, 1);
					player.sendMessage(Message.sendAdminInfoIfUsedCommandInRG.toString()
							.replace("<player>", playerName)
							.replace("<cmd>", cmd)
							.replace("<region>", regionName));
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
	public void notify(String playerName, String senderCommand, String regionName) {
		if (wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyConsole()) {
			for (String cmd : wgRegionProtect.getUtilConfig().getConfig().getSpyCommandList()) {
				if (cmd.equalsIgnoreCase(senderCommand.toLowerCase())) {
					Bukkit.getConsoleSender().sendMessage(Message.sendAdminInfoIfUsedCommandInRG.toString()
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
	public void notifyIfActionInRegion(Player admin, Player suspect, String time, String suspectName, RegionAction action, String regionName, double x, double y, double z, String world) {
		if (this.isSenderListenerPermission(suspect, IUtilPermissions.SPY_INSPECT_FOR_SUSPECT, null)
				&& wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdmin()) {
				admin.sendMessage(Message.sendAdminInfoIfActionInRegion.toString()
						.replace("<time>", time).replace("<player>", suspectName)
					.replace("<action>", action.getAction())
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
	public boolean isVersionSupported(){
		List<String> supportedVersions = Arrays.asList(
				"v1_18_R1", "v1_18_R2");
		String serverPackage = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			long time = System.currentTimeMillis();
			if (supportedVersions.contains(serverPackage)) {
				wgRegionProtect.getRsApi().getLogger().info("&7Loaded NMS hook in " + (System.currentTimeMillis()-time) + "ms");
				return true;
			} else {
				getLogger().info("&cNo compatibility issue was found, but this plugin version does not claim to support your server package (" + serverPackage + "). Disabling just to stay safe.");
			}
		} catch (Exception ex) {
			if (supportedVersions.contains(serverPackage)) {
				getLogger().error("&cYour server version is marked as compatible, but a compatibility issue was found. Please report the error below (include your server version & fork too)");
			} else {
				getLogger().error("&cYour server version is completely unsupported. Disabling.");
			}
		}
		return false;
	}

	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy - HH:mm");
		Date resultDate = new Date(System.currentTimeMillis());
		return sdf.format(resultDate);
	}

	public String getPathTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy-HH-mm");
		Date resultDate = new Date(System.currentTimeMillis());
		return sdf.format(resultDate);
	}

	public RSLogger getLogger() {
		return this.wgRegionProtect.getRsLogger();
	}
}