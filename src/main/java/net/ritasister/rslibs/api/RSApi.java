package net.ritasister.rslibs.api;

import java.util.List;

import net.ritasister.util.IUtilPermissions;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.jetbrains.annotations.Nullable;

public class RSApi {
	/**
	 * Check if player have permissions for use command.
	 * 
	 * @param sender Who send this command.
	 * @param cmd Name command.
	 * @param perm Get name permissions.
	 * @param message Get custom message for sender.
	 *
	 * @return isAuthCommandsPermission Have is Permissions or not.
	 */
	public static boolean isAuthCommandsPermission(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String perm, @Nullable String message) {
		if (!sender.hasPermission(perm) || !sender.isPermissionSet(perm)) {
			if (message != null) {
				sender.sendMessage(ChatApi.getColorCode(message));
			}return false;
		}return true;
	}
	/**
	 * Check if player have permissions for use TAB.
	 * 
	 * @param sender Who send this command.
	 * @param perm Get name permissions.
	 *
	 * @return isAuthCommandsPermissionsOnTab Have is Permissions or not.
	 */
	public static boolean isAuthCommandsPermissionsOnTab(@NotNull CommandSender sender, @NotNull String perm) {return sender.hasPermission(perm) && sender.isPermissionSet(perm);}
	/**
	 * Check if player have permissions for use Listener.
	 * 
	 * @param sender Who send this command.
	 * @param perm Get name permissions.
	 * @param message Get custom message for sender.
	 *
	 * @return isAuthListenerPermission Have is Permissions or not.
	 */
	public static boolean isAuthListenerPermission(@NotNull CommandSender sender, @NotNull String perm,  @Nullable String message) {
		if (!sender.hasPermission(perm) || !sender.isPermissionSet(perm)) {
			if (message != null) {
				sender.sendMessage(ChatApi.getColorCode(message));
			}return false;
		}return true;
	}
	/**
	 * Check if player have permissions for use Listener.
	 * 
	 * @param loc Get location of player.
	 * @param list Get list of regions from WorldGuard.
	 *
	 * @return checkStandingRegion Where is standing player.
	 */
	public static boolean checkStandingRegion(@NotNull Location loc, List<String> list) {
		final World world = loc.getWorld();
		final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
		final BlockVector3 locationBlockVector3 = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		final ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(locationBlockVector3);
		for (ProtectedRegion protectedRegion : applicableRegionSet) {
			for (String regionName : list) {
				if (protectedRegion.getId().equalsIgnoreCase(regionName)) {return true;}
			}
		}return false;
	}
	/**
	 * Get name of region for location
	 * 
	 * @param loc Get location of player.
	 * @param list Get list of regions from WorldGuard.
	 *
	 * @return regionName by Region where standing player.
	 */
	public static String getNameRegionFromConfig(@NotNull Location loc, List<String> list) {
		final World world = loc.getWorld();
		final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
		final BlockVector3 locationBlockVector3 = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		final ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(locationBlockVector3);
		for (ProtectedRegion protectedRegion : applicableRegionSet) {
			for (String regionName : list) {
				if (protectedRegion.getId().equalsIgnoreCase(regionName)) {return regionName;}
			}
		}return null;
	}
	/**
	 * Send notify to admin.
	 *
	 * @param player Get player object.
	 * @param playerName Get nickname player.
	 * @param senderCommand Get name command what player try to use in region.
	 * @param regionName Get region name, where player send command to server.
	 *
	 */
	public static void notifyAdmin(Player player, String playerName, String senderCommand, String regionName) {
		if(WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdmin) {
			if(RSApi.isAuthListenerPermission(player, IUtilPermissions.regionProtectNotifyAdmin, null)) {
				for(String cmd : WorldGuardRegionProtect.utilConfig.spyCommand) {
					if(cmd.equalsIgnoreCase(senderCommand.toLowerCase())) {
						if(WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdminPlaySoundEnable) {
							player.playSound(player.getLocation(), WorldGuardRegionProtect.utilConfig.spyCommandNotifyAdminPlaySound, 1, 1);
						}
						player.sendMessage(WorldGuardRegionProtect.utilConfigMessage.sendAminInfo
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
	 * @param playerName Get nickname player.
	 * @param senderCommand Get name command what player try to use in region.
	 * @param regionName Get region name, where player send command to server.
	 *
	 */
	public static void notifyConsole(String playerName, String senderCommand, String regionName) {
		if(WorldGuardRegionProtect.utilConfig.spyCommandNotifyConsole) {
			for(String cmd : WorldGuardRegionProtect.utilConfig.spyCommand) {
				if(cmd.equalsIgnoreCase(senderCommand.toLowerCase())) {
					Bukkit.getConsoleSender().sendMessage(WorldGuardRegionProtect.utilConfigMessage.sendAminInfo
							.replace("<player>", playerName)
							.replace("<cmd>", cmd)
							.replace("<rg>", regionName));
				}
			}
		}
	}
}