package net.ritasister.srp;

import java.util.*;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.permissions.*;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.*;
import com.sk89q.worldguard.protection.*;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.ritasister.util.UtilPermissions;

public class SRPApi 
{
	public static boolean isAuthCommandsPermission(CommandSender sender, Command cmd, UtilPermissions utilPermissions, String message) 
	{
		if (!sender.hasPermission(utilPermissions.getPerm()) || !sender.isPermissionSet(utilPermissions.getPerm())) 
		{
			if (message != null) 
			{
				sender.sendMessage(message.replaceAll("&", ""));
			}
			return false;
		}
		return true;
	}
	public static boolean isAuthCommandsPermissionsOnTab(CommandSender sender, UtilPermissions utilPermissions)
	{
		if (!sender.hasPermission(utilPermissions.getPerm()) || !sender.isPermissionSet(utilPermissions.getPerm())) 
		{
			return false;
		}
		return true;
	}
	public static boolean isAuthListenerPermission(CommandSender sender, UtilPermissions utilPermissions, String message) 
	{
		if (!sender.hasPermission(utilPermissions.getPerm()) || !sender.isPermissionSet(utilPermissions.getPerm())) 
		{
			if (message != null) 
			{
				sender.sendMessage(message);
			}
			return false;
		}
		return true;
	}
	//Проверка игрока в защищенном регионе.
	public static boolean checkStandingRegion(Location loc, List<String> regionName)
	{
		final World world = loc.getWorld();
		final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
		final BlockVector3 locationBlockVector3 = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		final ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(locationBlockVector3);
		for (ProtectedRegion protectedRegion : applicableRegionSet)
		{
			for (String regionList : regionName) 
			{
				if (protectedRegion.getId().equalsIgnoreCase(regionList))
				{
					return true;
				}
			}
		}
		return false;
	}
}