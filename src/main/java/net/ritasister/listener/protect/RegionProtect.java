package net.ritasister.listener.protect;

import java.util.*;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.*;
import org.bukkit.event.player.*;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.projectiles.*;

import com.sk89q.worldedit.bukkit.*;
import com.sk89q.worldedit.math.*;
import com.sk89q.worldguard.*;
import com.sk89q.worldguard.bukkit.*;
import com.sk89q.worldguard.protection.*;
import com.sk89q.worldguard.protection.managers.*;
import com.sk89q.worldguard.protection.regions.*;

import net.ritasister.rslibs.api.RSApi;
//import net.ritasister.srp.RSApi;
import net.ritasister.srp.ServerRegionProtect;
import net.ritasister.util.UtilPermissions;
import net.ritasister.util.wg.Iwg;
import net.ritasister.util.wg.wg7;

public class RegionProtect implements Listener
{
	private ServerRegionProtect serverRegionProtect;
	private Iwg wg;
	private final List<String> regionEditArgs = Arrays.asList("f", "flag");
	private final List<String> regionEditArgsFlags = Arrays.asList("-f", "-u", "-n", "-g", "-a");

	public RegionProtect(ServerRegionProtect serverRegionProtect)
	{
		this.serverRegionProtect=serverRegionProtect;
		this.wg = this.setUpWorldGuardVersionSeven();
	}
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	private void BBE(final BlockBreakEvent e) 
	{
		final Block b = e.getBlock();
		final Location loc = b.getLocation();
		if(RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtectAllow)
			|| RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtectOnlyBreakAllow))
		{
			e.setCancelled(false);
		}else
		if(RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtect)) 
		{
			final Player p = e.getPlayer();
			if(RSApi.isAuthListenerPermission(p, UtilPermissions.serverRegionProtect, null))return;
			{
				e.setCancelled(true);
				if (ServerRegionProtect.utilConfig.regionMessageProtect)
				{
					p.sendMessage(ServerRegionProtect.utilConfigMessage.srpMsg);  
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	private void BPE(final BlockPlaceEvent e)
	{
		final Block b = e.getBlock();
		final Location loc = b.getLocation();
		if(RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtectAllow))
		{
			e.setCancelled(false);
		}else
		if(RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtect)
				|| RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtectAllow)) 
		{
			final Player p = e.getPlayer();
			if(RSApi.isAuthListenerPermission(p, UtilPermissions.serverRegionProtect, null))return;
			{
				e.setCancelled(true);
				if (ServerRegionProtect.utilConfig.regionMessageProtect)
					p.sendMessage(ServerRegionProtect.utilConfigMessage.srpMsg); 
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	private void PBEE(final PlayerBucketEmptyEvent e) 
	{
		final Player p = e.getPlayer();
		final Location loc = e.getBlockClicked().getLocation();
		if(RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtect))
		{
			if(RSApi.isAuthListenerPermission(p, UtilPermissions.serverRegionProtect, null))return;
			{
				if (e.getBlockClicked().getType() == Material.LAVA_BUCKET
						&& e.getBlockClicked().getType() == Material.WATER_BUCKET
						&& e.getBlockClicked().getType() == Material.BUCKET)
				{
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	private void EDBEE(final EntityDamageByEntityEvent e) 
	{
		Entity entity = e.getEntity();
		Entity damager = e.getDamager();
		final Location loc = entity.getLocation();
		if(RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtect))
		{
			if(RSApi.isAuthListenerPermission(damager, UtilPermissions.serverRegionProtect, null))return;
			{
				if (entity instanceof ArmorStand 
						|| entity instanceof ItemFrame 
						|| entity instanceof Painting
						|| entity instanceof EnderCrystal)
				{
					if (damager instanceof Player)
					{
						e.setCancelled(true);
					}else if (damager instanceof Projectile) 
					{
						damager = ((Projectile) damager).getShooter() instanceof Entity ? (Entity) ((Projectile) damager).getShooter() : null;
						if (damager instanceof Player) 
						{
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	private void PIEE(final PlayerInteractEntityEvent e)
	{
		final Player p = e.getPlayer();
		final Location clickLoc = e.getRightClicked().getLocation();
		if(RSApi.checkStandingRegion(clickLoc, ServerRegionProtect.utilConfig.regionProtect))
		{
			if(RSApi.isAuthListenerPermission(p, UtilPermissions.serverRegionProtect, null))return;
			{
				if(e.getRightClicked().getType() == EntityType.ITEM_FRAME
						&& this.wg.wg(p.getWorld(), p.getLocation(), false))
				{
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	private void PASME(final PlayerArmorStandManipulateEvent e)
	{
		final Player p = e.getPlayer();
		final Location clickLoc = e.getRightClicked().getLocation();
		if(RSApi.checkStandingRegion(clickLoc, ServerRegionProtect.utilConfig.regionProtect))
		{
			if(RSApi.isAuthListenerPermission(p, UtilPermissions.serverRegionProtect, null))return;
			{
				if(e.getRightClicked().getType() == EntityType.ARMOR_STAND 
						&& this.wg.wg(p.getWorld(), clickLoc, false))
				{
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	private void SGE(final StructureGrowEvent e) 
	{
		if (this.wg.wg(e.getWorld(), e.getLocation(), false)) 
		{
			e.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	private void PIE(final PlayerInteractEvent e)
	{
		if (e.getItem() != null) 
		{
			Player p = e.getPlayer();
			if(RSApi.isAuthListenerPermission(p, UtilPermissions.serverRegionProtect, null))return;
			{
				for(String spawnEntityType : ServerRegionProtect.utilConfig.spawnEntityType)
				{
					if(e.getItem().getType() == Material.getMaterial(spawnEntityType.toUpperCase())
						&& e.getClickedBlock() != null && this.wg.wg(p.getWorld(), e.getClickedBlock().getLocation(), false))
					{
						e.setCancelled(true);
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	private void HBBEE(final HangingBreakByEntityEvent e)
	{
		Entity entity = e.getEntity();
		Entity damager = e.getRemover();
		final Location loc = entity.getLocation();
		if(RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtect))
		{
			if(RSApi.isAuthListenerPermission(damager, UtilPermissions.serverRegionProtect, null))return;
			{
				if (e.getRemover().getType() == EntityType.PLAYER) 
				{
					e.setCancelled(true);
				}
				if (entity instanceof ItemFrame 
					|| entity instanceof Painting) 
				{
					if (damager instanceof Player)
					{
						e.setCancelled(true);
					}else if (damager instanceof Projectile)
					{
						damager = ((Projectile) damager).getShooter() instanceof Entity ? (Entity) ((Projectile) damager).getShooter() : null;
						if (damager instanceof Player) 
						{
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	private void HPE(final HangingPlaceEvent e)
	{
		final Entity entity = e.getEntity();
		final Player p = (Player) e.getPlayer();
		final Location loc = entity.getLocation();
		if(RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtect))
		{
			if(RSApi.isAuthListenerPermission(p, UtilPermissions.serverRegionProtect, null))return;
			{
				if (p.getInventory().getItemInMainHand().getType() == Material.ITEM_FRAME
						|| p.getInventory().getItemInMainHand().getType() == Material.PAINTING) 
				{
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = false)
	private void EEE(final EntityExplodeEvent e) 
	{
		final Entity entity = e.getEntity();
		final Location loc = entity.getLocation();
		if(RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtect))
		{
			if(e.getEntityType() == EntityType.PRIMED_TNT 
					|| e.getEntityType() == EntityType.ENDER_CRYSTAL 
					|| e.getEntityType() == EntityType.MINECART_TNT) 
			{
				e.blockList().clear();
			}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = false)
	public void BEE(final BlockExplodeEvent e) 
	{
		final Block b = e.getBlock();
		final Location loc = b.getLocation();
		if(RSApi.checkStandingRegion(loc, ServerRegionProtect.utilConfig.regionProtect))
		{
			if(b.getType() == null)return;
			{
				if(b.getType() == Material.RESPAWN_ANCHOR)return;
				{
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	private void PCPE(final PlayerCommandPreprocessEvent e) 
	{
		final Player p = e.getPlayer();
		final String[] s = e.getMessage().toLowerCase().split(" ");
		if(RSApi.isAuthListenerPermission(p, UtilPermissions.serverRegionProtect, null))return;
		{
			if (this.cmdWE(s[0]) && !this.wg.checkIntersection(p)) 
			{
				e.setCancelled(true);
			}
			if (this.cmdWE_C(s[0]) && !this.wg.checkCIntersection(p, s)) 
			{
				e.setCancelled(true);
			}
			if (this.cmdWE_P(s[0]) && !this.wg.checkPIntersection(p, s)) 
			{
				e.setCancelled(true);
			}
			if (this.cmdWE_S(s[0]) && !this.wg.checkSIntersection(p, s)) 
			{
				e.setCancelled(true);
			}
			if (this.cmdWE_U(s[0]) && !this.wg.checkUIntersection(p, s)) 
			{
				e.setCancelled(true);
			}
			if (this.cmdWE_CP(s[0])) 
			{
				e.setMessage(e.getMessage().replace("-o", ""));
				if (!this.wg.checkCPIntersection(p, s)) 
				{
					e.setCancelled(true);
				}
			}
			if ((s[0].equalsIgnoreCase("/rg") 
					|| s[0].equalsIgnoreCase("/region") 
					|| s[0].equalsIgnoreCase("/regions") 
					|| s[0].equalsIgnoreCase("/worldguard:rg") 
					|| s[0].equalsIgnoreCase("/worldguard:region") 
					|| s[0].equalsIgnoreCase("/worldguard:regions")) && s.length > 2) 
			{
				for (final String list : ServerRegionProtect.utilConfig.regionProtect) 
				{
					if (list.equalsIgnoreCase(s[2])) 
					{
						e.setCancelled(true);
					}
				}
				for (final String list : ServerRegionProtect.utilConfig.regionProtectOnlyBreakAllow)
				{
					if (list.equalsIgnoreCase(s[2])) 
					{
						e.setCancelled(true);
					}
				}
				if (s.length > 3 && this.regionEditArgs.contains(s[2].toLowerCase()) 
						|| s.length > 3 && this.regionEditArgsFlags.contains(s[2].toLowerCase()))
				{
					for (final String list : ServerRegionProtect.utilConfig.regionProtect) 
					{
						if (list.equalsIgnoreCase(s[3])) 
						{
							e.setCancelled(true);
						}
					}
					for (final String list : ServerRegionProtect.utilConfig.regionProtectOnlyBreakAllow) 
					{
						if (list.equalsIgnoreCase(s[3])) 
						{
							e.setCancelled(true);
						}
					}
				}
				if(s.length > 4 && s[2].equalsIgnoreCase("-w")
						|| s.length > 4 && this.regionEditArgsFlags.contains(s[2].toLowerCase()))
				{
					for (final String list : ServerRegionProtect.utilConfig.regionProtect) 
					{
						if (list.equalsIgnoreCase(s[4])) 
						{
							e.setCancelled(true);
						}
					}
					for (final String list : ServerRegionProtect.utilConfig.regionProtectOnlyBreakAllow) 
					{
						if (list.equalsIgnoreCase(s[4])) 
						{
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 5 && s[3].equalsIgnoreCase("-w")
						|| s.length > 5 && this.regionEditArgs.contains(s[4].toLowerCase())
						|| s.length > 5 && this.regionEditArgsFlags.contains(s[4].toLowerCase())) 
				{
					for (final String list : ServerRegionProtect.utilConfig.regionProtect) 
					{
						if (list.equalsIgnoreCase(s[5])) 
						{
							e.setCancelled(true);
						}
					}
					for (final String list : ServerRegionProtect.utilConfig.regionProtectOnlyBreakAllow) 
					{
						if (list.equalsIgnoreCase(s[5])) 
						{
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 6 && s[4].equalsIgnoreCase("-w")
						|| s.length > 6 && s[4].equalsIgnoreCase("-h")
						|| s.length > 6 && s[4].equalsIgnoreCase("-a")
						|| s.length > 6 && this.regionEditArgs.contains(s[5].toLowerCase())
						|| s.length > 6 && this.regionEditArgsFlags.contains(s[5].toLowerCase())) 
				{
					for (final String list : ServerRegionProtect.utilConfig.regionProtect) 
					{
						if (list.equalsIgnoreCase(s[6])) 
						{
							e.setCancelled(true);
						}
					}
					for (final String list : ServerRegionProtect.utilConfig.regionProtectOnlyBreakAllow) 
					{
						if (list.equalsIgnoreCase(s[6])) 
						{
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	private Iwg setUpWorldGuardVersionSeven() 
	{
		try{
			Class.forName("com.sk89q.worldedit.math.BlockVector3");
			return new wg7(this.serverRegionProtect);
		}catch(ClassNotFoundException ex){}
		return wg;
	}
	private boolean cmdWE(String s) 
	{
		s = s.replace("worldedit:", "");
		for(String tmp : ServerRegionProtect.utilConfig.CmdsWE) 
		{
			if (tmp.equalsIgnoreCase(s.toLowerCase())) 
			{
				return true;
			}
		}
		return false;
	}
	private boolean cmdWE_C(String s) 
	{
		s = s.replace("worldedit:", "");
		for(String tmp : ServerRegionProtect.utilConfig.cmdweC) 
		{
			if (tmp.equalsIgnoreCase(s.toLowerCase())) 
			{
				return true;
			}
		}
		return false;
	}
	private boolean cmdWE_P(String s) 
	{
		s = s.replace("worldedit:", "");
		for(String tmp : ServerRegionProtect.utilConfig.cmdweP) 
		{
			if (tmp.equalsIgnoreCase(s.toLowerCase())) 
			{
				return true;
			}
		}
		return false;
	}
	private boolean cmdWE_S(String s) 
	{
		s = s.replace("worldedit:", "");
		for(String tmp : ServerRegionProtect.utilConfig.cmdweS) 
		{
			if (tmp.equalsIgnoreCase(s.toLowerCase())) 
			{
				return true;
			}
		}
		return false;
	}
	private boolean cmdWE_U(String s) 
	{
		s = s.replace("worldedit:", "");
		for(String tmp : ServerRegionProtect.utilConfig.cmdweU) 
		{
			if (tmp.equalsIgnoreCase(s.toLowerCase())) 
			{
				return true;
			}
		}
		return false;
	}
	private boolean cmdWE_CP(String s) 
	{
		s = s.replace("worldedit:", "");
		for(String tmp : ServerRegionProtect.utilConfig.cmdweCP) 
		{
			if (tmp.equalsIgnoreCase(s.toLowerCase())) 
			{
				return true;
			}
		}
		return false;
	}
}