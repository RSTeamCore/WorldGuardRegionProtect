package net.rsteamcore.wgrp.rslibs.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.rsteamcore.wgrp.WorldGuardRegionProtect;
import net.rsteamcore.wgrp.rslibs.permissions.UtilPermissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Api class for other classes to use the necessary methods and other.
 */
@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class RSApi {

	private final WorldGuardRegionProtect wgRegionProtect;

	public void messageToCommandSender(@NotNull CommandSender commandSender, String message) {
		var miniMessage = MiniMessage.miniMessage();
		Component parsed = miniMessage.deserialize(message);
		commandSender.sendMessage(parsed);
	}

	public void messageToConsoleSender(@NotNull ConsoleCommandSender sender, String message) {
		var miniMessage = MiniMessage.miniMessage();
		Component parsed = miniMessage.deserialize(message);
		sender.sendMessage(parsed);
	}

	/**
	 * Check if a player has permission for use Listener.
	 *
	 * @param player  Who send this command.
	 * @param perm    Permission to check.
	 * @return If Player can use event method.
	 */
	public boolean isPlayerListenerPermission(@NotNull Player player, @NotNull UtilPermissions perm) {
		return player.hasPermission(perm.getPermissionName());
	}

	/**
	 * Check if an entity has permission for use Listener.
	 *
	 * @param entity who send this command.
	 * @param perm permission to check.
	 * @return If entity can use event method.
	 */
	public boolean isEntityListenerPermission(@NotNull Entity entity, @NotNull UtilPermissions perm) {
		return !entity.hasPermission(perm.getPermissionName());
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
		if (wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdminEnable() &&
				this.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT_NOTIFY_ADMIN)) {
			for (String cmd : wgRegionProtect.getUtilConfig().getConfig().getSpyCommandList()) {
				if (cmd.equalsIgnoreCase(senderCommand.toLowerCase())
						&& wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdminPlaySoundEnable()) {
					player.playSound(player.getLocation(), wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdminPlaySound().toLowerCase(), 1, 1);
					player.sendMessage(
							wgRegionProtect.getUtilConfig().getMessages().get("messages.Notify.sendAdminInfoIfUsedCommandInRG").toString()
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
		if (wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyConsoleEnable()) {
			for (String cmd : wgRegionProtect.getUtilConfig().getConfig().getSpyCommandList()) {
				if (cmd.equalsIgnoreCase(senderCommand.toLowerCase())) {
					Bukkit.getConsoleSender().sendMessage(
							wgRegionProtect.getUtilConfig().getMessages().get("messages.Notify.sendAdminInfoIfUsedCommandInRG").toString()
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
		if (this.isPlayerListenerPermission(suspectPlayer, UtilPermissions.SPY_INSPECT_FOR_SUSPECT)
				&& wgRegionProtect.getUtilConfig().getConfig().getSpyCommandNotifyAdminEnable()) {
				admin.sendMessage(
						wgRegionProtect.getUtilConfig().getMessages().get("messages.Notify.sendAdminInfoIfActionInRegion").toString()
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
		List<String> supportedVersions = Arrays.asList("v1_19_R1", "v1_19_R2");
		String serverPackage = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			long time = System.currentTimeMillis();
			if (supportedVersions.contains(serverPackage)) {
				wgRegionProtect.getLogger().info("Loaded NMS hook in " + (System.currentTimeMillis()-time) + "ms");
				return true;
			} else {
				wgRegionProtect.getLogger().info("No compatibility issue was found, but this plugin version does not claim to support your server package (" + serverPackage + ").");
			}
		} catch (Exception ex) {
			if (supportedVersions.contains(serverPackage)) {
				wgRegionProtect.getLogger().error("Your server version is marked as compatible, but a compatibility issue was found. Please report the error below (include your server version & fork too)");
			} else {
				wgRegionProtect.getLogger().error("Your server version is completely unsupported. Disabling.");
			}
		}
		return false;
	}
}