package net.ritasister.rslibs.api;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.ritasister.rslibs.chat.api.ChatApi;
import net.ritasister.rslibs.permissions.IUtilPermissions;
import net.ritasister.util.Time;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RSApi {

	/**
	 * Check if a player has permissions for commands.
	 * 
	 * @param sender Who send this command.
	 * @param cmd Name command.
	 * @param perm Permission to check.
	 * @param message return custom message for sender.
	 *
	 * @return isSenderCommandsPermission if Sender can use commands.
	 */
	public static boolean isSenderCommandsPermission(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String perm, String message) {
		if (!sender.hasPermission(perm) || !sender.isPermissionSet(perm)) {
			if (message != null) {
				sender.sendMessage(ChatApi.getColorCode(message));
			}return false;
		}return true;
	}
	/**
	 * Check if a player has permission for use TAB.
	 * 
	 * @param sender Who send this command.
	 * @param perm Permission to check.
	 *
	 * @return isAuthCommandsPermissionsOnTab if Sender can use TAB.
	 *
	 */
	public static boolean isSenderCommandsPermissionOnTab(@NotNull CommandSender sender, @NotNull String perm) {
		return sender.hasPermission(perm) && sender.isPermissionSet(perm);
	}
	/**
	 * Check if a player has permission for use Listener.
	 * 
	 * @param sender Who send this command.
	 * @param perm Permission to check.
	 * @param message return custom message for sender.
	 *
	 * @return isAuthListenerPermission if Sender can use Events.
	 */
	public static boolean isSenderListenerPermission(@NotNull CommandSender sender, @NotNull String perm, String message) {
		if (!sender.hasPermission(perm) || !sender.isPermissionSet(perm)) {
			if (message != null) {
				sender.sendMessage(ChatApi.getColorCode(message));
			}return false;
		}return true;
	}

	/**
	 * Check access in standing region by player used region name from config.yml.
	 * 
	 * @param loc return location of player.
	 * @param list return list of regions from WorldGuard.
	 *
	 * @return checkStandingRegion return location of Player.
	 */
	public static boolean checkStandingRegion(@NotNull Location loc, List<String> list) {
		final World world = loc.getWorld();
		final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
		final BlockVector3 locationBlockVector3 = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		final ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(locationBlockVector3);
		for (ProtectedRegion protectedRegion : applicableRegionSet) {
			for (String regionName : list) {
				if (protectedRegion.getId().equalsIgnoreCase(regionName)) {
					return true;
				}
			}
		}return false;
	}

	/**
	 * Check access in standing region by player without region name.
	 *
	 * @param loc return location of player.
	 *
	 * @return checkStandingRegion return location of Player.
	 */
	public static boolean checkStandingRegion(@NotNull Location loc) {
		final World world = loc.getWorld();
		final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
		final BlockVector3 locationBlockVector3 = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		final ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(locationBlockVector3);
		return (applicableRegionSet.size() != 0);
	}

	/**
	 * Getting region name for another method.
	 *
	 * @param loc return location of player.
	 *
	 * @return getProtectRegionName Where is standing player.
	 */
	public static String getProtectRegionName(@NotNull Location loc) {
		final World world = loc.getWorld();
		final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
		final BlockVector3 locationBlockVector3 = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		final ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(locationBlockVector3);
		for (ProtectedRegion protectedRegion : applicableRegionSet) {
			return protectedRegion.getId();
		}
		return null;
	}

	/**
	 * Send notification to admin.
	 * @param player return player object.
	 * @param playerName return nickname player.
	 * @param senderCommand return name command if player attempt to use in region.
	 * @param regionName return region name, if player attempts to use command in region.
	 *
	 */
	public static void notify(Player player, String playerName, String senderCommand, String regionName) {
		if(WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdmin) {
			if(RSApi.isSenderListenerPermission(player, IUtilPermissions.regionProtectNotifyAdmin, null)) {
				for(String cmd : WorldGuardRegionProtect.utilConfig.spyCommand) {
					if(cmd.equalsIgnoreCase(senderCommand.toLowerCase())) {
						if(WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdminPlaySoundEnable) {
							player.playSound(player.getLocation(), WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdminPlaySound, 1, 1);
						}	player.sendMessage(WorldGuardRegionProtect.utilConfigMessage.sendAdminInfoIfUsedCommandWithRG
								.replace("<player>", playerName)
								.replace("<cmd>", cmd)
								.replace("<rg>", regionName));
					}
				}
			}
		}
	}
	/**
	 * Send notify to admin.
	 *
	 * @param playerName return player object.
	 * @param senderCommand return name command if player attempt to use in region.
	 * @param regionName return region name, if player attempts to use command in region.
	 *
	 */
	public static void notify(String playerName, String senderCommand, String regionName) {
		if(WorldGuardRegionProtect.utilConfig.spyCommandNotifyConsole) {
			for(String cmd : WorldGuardRegionProtect.utilConfig.spyCommand) {
				if(cmd.equalsIgnoreCase(senderCommand.toLowerCase())) {
					Bukkit.getConsoleSender().sendMessage(WorldGuardRegionProtect.utilConfigMessage.sendAdminInfoIfUsedCommandWithRG
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
	 * @param admin return message for admin who destroys region.
	 * @param suspect return object player for method.
	 * @param playerName return player name who's interacting with region.
	 * @param regionName return region name.
	 * @param time return time if a region is broken of player.
	 * @param x return X position of block.
	 * @param y return Y position of block.
	 * @param z return Z position of block.
	 * @param world return world position of block.
	 *
	 */
	public static void notifyIfBreakInRegion(Player admin, Player suspect, String playerName, String time, String regionName, double x, double y, double z, String world) {
		if(RSApi.isSenderListenerPermission(suspect, IUtilPermissions.spyInspectForSuspect, null)) {
			if(WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdmin) {
				admin.sendMessage(WorldGuardRegionProtect.utilConfigMessage.sendAdminInfoIfBreakInRegion
						.replace("<time>", time).replace("<player>", playerName)
						.replace("<region>", regionName)
						.replace("<x>", String.valueOf(x))
						.replace("<y>", String.valueOf(y))
						.replace("<z>", String.valueOf(z))
						.replace("<world>", world));
			}
		}
	}
	/**
	 * Send notification if player attempts to interact with region from WorldGuard.
	 *
	 * @param admin return message for admin if player builds on region.
	 * @param suspect return object player for method.
	 * @param playerName return player name who's interacting with region.
	 * @param regionName return region name.
	 * @param time return time if a region is placed of player.
	 * @param x return X position of block.
	 * @param y return Y position of block.
	 * @param z return Z position of block.
	 * @param world return world position of block.
	 *
	 */
	public static void notifyIfPlaceInRegion(Player admin, Player suspect, String playerName, String time, String regionName, double x, double y, double z, String world) {
		if(RSApi.isSenderListenerPermission(suspect, IUtilPermissions.spyInspectForSuspect, null)) {
			if(WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdmin) {
				admin.sendMessage(WorldGuardRegionProtect.utilConfigMessage.sendAdminInfoIfPlaceInRegion
						.replace("<time>", time).replace("<player>", playerName)
						.replace("<region>", regionName)
						.replace("<x>", String.valueOf(x))
						.replace("<y>", String.valueOf(y))
						.replace("<z>", String.valueOf(z))
						.replace("<world>", world));
			}
		}
	}
	/**
	 * Initializes all used NMS classes, constructors, fields and methods.
	 * Returns {@code true} if everything went successfully and version is marked as compatible,
	 * {@code false} if anything went wrong or version is not marked as compatible.
	 * @return    {@code true} if server version is compatible, {@code false} if not
	 */
	public static boolean isVersionSupported(){
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

	public static String getTime() {
		Date date = new Date();
		final long currentTime = (System.currentTimeMillis() - date.getTime()) / 1000L;
		return Time.getTimeToString((int)currentTime, 1, true);
	}

	public static String getRegionNameString(Location loc) {
		String regionName;
		return regionName = RSApi.getProtectRegionName(loc);
	}
}