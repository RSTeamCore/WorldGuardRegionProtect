package net.ritasister.rslibs.api;

import java.util.List;

import net.ritasister.util.IUtilPermissions;
import net.ritasister.util.Time;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;
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
	public static boolean isAuthCommandsPermission(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String perm, String message) {
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
	public static boolean isAuthListenerPermission(@NotNull CommandSender sender, @NotNull String perm, String message) {
		if (!sender.hasPermission(perm) || !sender.isPermissionSet(perm)) {
			if (message != null) {
				sender.sendMessage(ChatApi.getColorCode(message));
			}return false;
		}return true;
	}

	/**
	 * Check access in standing region by player used region name from config.yml.
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
				if (protectedRegion.getId().equalsIgnoreCase(regionName)) {
					return true;
				}
			}
		}return false;
	}

	/**
	 * Check access in standing region by player without region name.
	 *
	 * @param loc Get location of player.
	 *
	 * @return checkStandingRegion Where is standing player.
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
	 * @param loc Get location of player.
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
	 * Send notify to admin.
	 * @param player Get player object.
	 * @param playerName Get nickname player.
	 * @param senderCommand Get name command what player try to use in region.
	 * @param regionName Get region name, where player send command to server.
	 *
	 */
	public static void notifyAdmin(Player player, String playerName, String senderCommand, String regionName) {
		if(WorldGuardRegionProtect.instance.utilConfig.spyCommandNotifyAdmin()) {
			if(RSApi.isAuthListenerPermission(player, IUtilPermissions.regionProtectNotifyAdmin, null)) {
				for(String cmd : WorldGuardRegionProtect.instance.utilConfig.spyCommand()) {
					if(cmd.equalsIgnoreCase(senderCommand.toLowerCase())) {
						if(WorldGuardRegionProtect.instance.utilConfig.spyCommandNotifyAdminPlaySoundEnable()) {
							player.playSound(player.getLocation(), WorldGuardRegionProtect.instance.utilConfig.spyCommandNotifyAdminPlaySound(), 1, 1);
						}	player.sendMessage(WorldGuardRegionProtect.instance.utilConfigMessage.sendAdminInfoIfUsedCommandWithRG()
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
		if(WorldGuardRegionProtect.instance.utilConfig.spyCommandNotifyConsole()) {
			for(String cmd : WorldGuardRegionProtect.instance.utilConfig.spyCommand()) {
				if(cmd.equalsIgnoreCase(senderCommand.toLowerCase())) {
					Bukkit.getConsoleSender().sendMessage(WorldGuardRegionProtect.instance.utilConfigMessage.sendAdminInfoIfUsedCommandWithRG()
							.replace("<player>", playerName)
							.replace("<cmd>", cmd)
							.replace("<region>", regionName));
				}
			}
		}
	}
	/**
	 * Send notify if player try to interact with region from WorldGuard.
	 *
	 * @param admin who's can give message who destroy region
	 * @param suspect get object player for method
	 * @param playerName get player name who's interact with region
	 * @param regionName get region name
	 * @param time get time placed anything
	 * @param x get X position of block
	 * @param y get Y position of block
	 * @param z get Z position of block
	 * @param world get world position of block
	 *
	 */
	public static void notifyIfBreakInRegion(Player admin, Player suspect, String playerName, String time, String regionName, double x, double y, double z, String world) {
		if(RSApi.isAuthListenerPermission(suspect, IUtilPermissions.spyInspectForSuspect, null)) {
			if(WorldGuardRegionProtect.instance.utilConfig.spyCommandNotifyAdmin()) {
				admin.sendMessage(WorldGuardRegionProtect.instance.utilConfigMessage.sendAdminInfoIfBreakInRegion()
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
	 * Send notify if player try to interact with region from WorldGuard.
	 *
	 * @param admin who's can give message who destroy region
	 * @param suspect get object player for method
	 * @param playerName get player name who's interact with region
	 * @param regionName get region name
	 * @param time get time placed anything
	 * @param x get X position of block
	 * @param y get Y position of block
	 * @param z get Z position of block
	 * @param world get world position of block
	 *
	 */
	public static void notifyIfPlaceInRegion(Player admin, Player suspect, String playerName, String time, String regionName, double x, double y, double z, String world) {
		if(RSApi.isAuthListenerPermission(suspect, IUtilPermissions.spyInspectForSuspect, null)) {
			if(WorldGuardRegionProtect.instance.utilConfig.spyCommandNotifyAdmin()) {
				admin.sendMessage(WorldGuardRegionProtect.instance.utilConfigMessage.sendAdminInfoIfBreakInRegion()
						.replace("<time>", time).replace("<player>", playerName)
						.replace("<region>", regionName)
						.replace("<x>", String.valueOf(x))
						.replace("<y>", String.valueOf(y))
						.replace("<z>", String.valueOf(z))
						.replace("<world>", world));
			}
		}
	}
}