package net.ritasister.wgrp.rslibs.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that contains methods about rights, notification and other
 */
@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class RSApi {

	private final WorldGuardRegionProtect wgRegionProtect;

	/**
	 * Check if a sender has permissions for commands.
	 *
	 * @param sender Who send this command.
	 * @param cmd Name command.
	 * @param perm Permission to check.
	 * @param message Return custom message for a sender.
	 * @return If Sender can use commands.
	 */
	public boolean isSenderCommandsPermission(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull UtilPermissions perm, String message) {
		if (!sender.hasPermission(perm.getPermissionName())) {
			if (message != null) {
				sender.sendMessage(Component.text(message));
			}
			return true;
		}
		return false;
	}

	/**
	 * Check if a Player has permissions for commands.
	 *
	 * @param player Who send this command.
	 * @param cmd Name command.
	 * @param perm Permission to check.
	 * @param message Return custom message for a Player.
	 * @return If sender can use commands.
	 */
	public boolean isSenderCommandsPermission(@NotNull Player player, @NotNull Command cmd, @NotNull UtilPermissions perm, String message) {
		if (!player.hasPermission(perm.getPermissionName())) {
			if (message != null) {
				player.sendMessage(Component.text(message));
			}
			return true;
		}
		return false;
	}

	/**
	 * Check if a sender has permission for use TAB.
	 *
	 * @param sender Who send this command.
	 * @param perm Permission to check.
	 * @return If sender can use TAB.
	 */
	public boolean isSenderCommandsPermissionOnTab(@NotNull CommandSender sender, @NotNull UtilPermissions perm) {
		return sender.hasPermission(perm.getPermissionName());
	}

	/**
	 * Check if a sender has permission for use Listener.
	 *
	 * @param sender Who send this command.
	 * @param perm Permission to check.
	 * @param message Return custom message for a sender.
	 * @return If sender can use Events.
	 */
	public boolean isSenderListenerPermission(@NotNull CommandSender sender, @NotNull UtilPermissions perm, String message) {
		if (!sender.hasPermission(perm.getPermissionName())) {
			if (message != null) {
				sender.sendMessage(Component.text(message));
			}
			return false;
		}
		return true;
	}

	/**
	 * Check if a player has permission for use Listener.
	 *
	 * @param player  Who send this command.
	 * @param perm    Permission to check.
	 * @param message Return custom message for a player.
	 * @return If Player can use event method.
	 */
	public boolean isSenderListenerPermission(@NotNull Player player, @NotNull UtilPermissions perm, String message) {
		if (!player.hasPermission(perm.getPermissionName())) {
			if (message != null) {
				player.sendMessage(Component.text(message));
			}
			return false;
		}
		return true;
	}

	/**
	 * Check if an entity has permission for use Listener.
	 *
	 * @param entity who send this command.
	 * @param perm permission to check.
	 * @param message Return custom message for an entity.
	 * @return If entity can use event method.
	 */
	public boolean isSenderListenerPermission(@NotNull Entity entity, @NotNull UtilPermissions perm, String message) {
		if (entity.hasPermission(perm.getPermissionName())) {
			if (message != null) {
				entity.sendMessage(Component.text(message));
			}
			return false;
		}
		return true;
	}

	/**
	 * Send notification to admin.
	 *
	 * @param player Player object.
	 * @param playerName Nickname player.
	 * @param senderCommand Name command if Player attempt to use in a region.
	 * @param regionName The region name, if Player attempts to use command in a region.
	 */
	public void notify(Player player, String playerName, String senderCommand, String regionName) {
		if(regionName == null) return;
		if (wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdmin()
				&& this.isSenderListenerPermission(player, UtilPermissions.REGION_PROTECT_NOTIFY_ADMIN, null)) {
			for (String cmd : wgRegionProtect.getUtilConfig().getConfig().getSpyCommandList()) {
				if (cmd.equalsIgnoreCase(senderCommand.toLowerCase())
						&& wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdminPlaySoundEnable()) {
					player.playSound(player.getLocation(), wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdminPlaySound().toLowerCase(), 1, 1);
					player.sendMessage(Message.Notify_sendAdminInfoIfUsedCommandInRG.toString()
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
	 * @param playerName Player object.
	 * @param senderCommand Name command if Player attempt to use in a region.
	 * @param regionName Region name, if Player attempts to use command in a region.
	 */
	public void notify(String playerName, String senderCommand, String regionName) {
		if(regionName == null) return;
		if (wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyConsole()) {
			for (String cmd : wgRegionProtect.getUtilConfig().getConfig().getSpyCommandList()) {
				if (cmd.equalsIgnoreCase(senderCommand.toLowerCase())) {
					Bukkit.getConsoleSender().sendMessage(Message.Notify_sendAdminInfoIfUsedCommandInRG.toString()
							.replace("<player>", playerName)
							.replace("<cmd>", cmd)
							.replace("<region>", regionName));
				}
			}
		}
	}

	/**
	 * Send a notification to the administrator if Player attempts to interact with a region from WorldGuard.
	 *
	 * @param admin Message for an admin who destroys a region.
	 * @param suspectPlayer Object player for method.
	 * @param suspectName Player name who interacting with a region.
	 * @param action Get the actions.
	 * @param regionName Region name.
	 * @param x Position of block.
	 * @param y Position of block.
	 * @param z Position of block.
	 * @param world Position of block.
	 */
	public void notifyIfActionInRegion(Player admin, Player suspectPlayer, String suspectName, RegionAction action, String regionName, double x, double y, double z, String world) {
		if (this.isSenderListenerPermission(suspectPlayer, UtilPermissions.SPY_INSPECT_FOR_SUSPECT, null)
				&& wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdmin()) {
				admin.sendMessage(Message.Notify_sendAdminInfoIfActionInRegion.toString()
						.replace("<player>", suspectName)
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
	 * Returns {@code true} if everything went successfully and version marked as compatible,
	 * {@code false} if anything went wrong or version not marked as compatible.
	 * @return    {@code true} if server version compatible, {@code false} if not
	 */
	public boolean isVersionSupported(){
		String supportedVersions = "v1_19_R1";
		String serverPackage = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			long time = System.currentTimeMillis();
			if (supportedVersions.contains(serverPackage)) {
				wgRegionProtect.getLogger().info(Component.text("&7Loaded NMS hook in " + (System.currentTimeMillis()-time) + "ms"));
				return true;
			} else {
				wgRegionProtect.getLogger().info(Component.text("&cNo compatibility issue was found, but this plugin version does not claim to support your server package (" + serverPackage + ")."));
			}
		} catch (Exception ex) {
			if (supportedVersions.contains(serverPackage)) {
				wgRegionProtect.getLogger().error(Component.text("&cYour server version is marked as compatible, but a compatibility issue was found. Please report the error below (include your server version & fork too)"));
			} else {
				wgRegionProtect.getLogger().error(Component.text("&cYour server version is completely unsupported. Disabling."));
			}
		}
		return false;
	}

	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy - HH:mm");
		Date resultDate = new Date(System.currentTimeMillis());
		return sdf.format(resultDate);
	}
}