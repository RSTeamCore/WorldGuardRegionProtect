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

import net.ritasister.rslibs.api.RSApi;
import net.ritasister.util.IUtilPermissions;
import net.ritasister.util.wg.Iwg;
import net.ritasister.util.wg.wg7;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class RegionProtect implements Listener {

	private final WorldGuardRegionProtect worldGuardRegionProtect;
	private final Iwg wg;
	private final List<String> regionCommandNameArgs = Arrays.asList(
			"/rg", "/region", "/regions",
			"/worldguard:rg", "/worldguard:region", "/worldguard:regions");
	private final List<String> regionEditArgs = Arrays.asList("f", "flag");
	private final List<String> regionEditArgsFlags = Arrays.asList("-f", "-u", "-n", "-g", "-a");

	public RegionProtect(WorldGuardRegionProtect worldGuardRegionProtect) {
		this.worldGuardRegionProtect=worldGuardRegionProtect;
		this.wg=this.setUpWorldGuardVersionSeven();
	}
	@EventHandler(priority = EventPriority.LOW)
	private void denyBreak(final BlockBreakEvent e) {
		final String playerName = e.getPlayer().getPlayerProfile().getName();
		final Block b = e.getBlock();
		final Location loc = b.getLocation();
		final String RegionName = String.valueOf(RSApi.getProtectRegionName(loc));
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtectAllow)
			|| RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtectOnlyBreakAllow)) {
			e.setCancelled(false);
		} else if (RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			final Player p = e.getPlayer();
			if (!RSApi.isAuthListenerPermission(p, IUtilPermissions.regionProtect, null)) {{
					e.setCancelled(true);
					if (WorldGuardRegionProtect.utilConfig.regionMessageProtect) {
						p.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsg);
					}
				}
			}
		} else if(RSApi.checkStandingRegion(loc)){
			RSApi.notifyInteract(playerName, RegionName);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	private void denyPlace(final BlockPlaceEvent e) {
		final String playerName = e.getPlayer().getPlayerProfile().getName();
		final Block b = e.getBlock();
		final Location loc = b.getLocation();
		final String RegionName = String.valueOf(RSApi.getProtectRegionName(loc));
		if (RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtectAllow)) {
			e.setCancelled(false);
		} else if (RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)
				|| RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtectAllow)) {
			final Player p = e.getPlayer();
			if (RSApi.isAuthListenerPermission(p, IUtilPermissions.regionProtect, null)) return;{
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtect) {
					p.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsg);
				}
			}
		} else if(RSApi.checkStandingRegion(loc)){
			RSApi.notifyInteract(playerName, RegionName);
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyUseBucket(final PlayerBucketEmptyEvent e) {
		final Player p = e.getPlayer();
		final Location loc = e.getBlockClicked().getLocation();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(RSApi.isAuthListenerPermission(p, IUtilPermissions.regionProtect, WorldGuardRegionProtect.utilConfigMessage.wgrpMsg))return;{
				if (e.getBlockClicked().getType() == Material.LAVA_BUCKET
						|| e.getBlockClicked().getType() == Material.WATER_BUCKET
						|| e.getBlockClicked().getType() == Material.BUCKET) {
					e.setCancelled(true);
				}e.setCancelled(true);
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyEntityDamageByEntityEvent(final EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		Entity attacker = e.getDamager();
		final Location loc = entity.getLocation();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(RSApi.isAuthListenerPermission(attacker, IUtilPermissions.regionProtect, null))return;{
				if (entity instanceof ArmorStand
						|| entity instanceof ItemFrame
						|| entity instanceof GlowItemFrame
						|| entity instanceof Painting
						|| entity instanceof EnderCrystal) {
					if (attacker instanceof Player) {
						e.setCancelled(true);
					}else if (attacker instanceof Projectile){
						attacker = ((Projectile) attacker).getShooter() instanceof Entity ? (Entity) ((Projectile) attacker).getShooter() : null;
						if (attacker instanceof Player) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyInteractWithEntity(final PlayerInteractEntityEvent e) {
		final Player player = e.getPlayer();
		final Location clickLoc = e.getRightClicked().getLocation();
		if(RSApi.checkStandingRegion(clickLoc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(RSApi.isAuthListenerPermission(player, IUtilPermissions.regionProtect, null))return;{
				if(e.getRightClicked().getType() == EntityType.ITEM_FRAME
						|| e.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME
						&& this.wg.wg(player.getWorld(), player.getLocation(), false)) {e.setCancelled(true);}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyManipulateArmorStand(final PlayerArmorStandManipulateEvent e) {
		final Player p = e.getPlayer();
		final Location clickLoc = e.getRightClicked().getLocation();
		if(RSApi.checkStandingRegion(clickLoc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(RSApi.isAuthListenerPermission(p, IUtilPermissions.regionProtect, null))return;{
				if(e.getRightClicked().getType() == EntityType.ARMOR_STAND 
						&& this.wg.wg(p.getWorld(), clickLoc, false)) {e.setCancelled(true);}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyStructureGrow(final StructureGrowEvent e) {
		if (this.wg.wg(e.getWorld(), e.getLocation(), false)) {e.setCancelled(true);}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyInteract(final PlayerInteractEvent e) {
		if (e.getItem() != null) {
			Player p = e.getPlayer();
			if(RSApi.isAuthListenerPermission(p, IUtilPermissions.regionProtect, null))return;{
				for(String spawnEntityType : WorldGuardRegionProtect.utilConfig.spawnEntityType) {
					if(e.getItem().getType() == Material.getMaterial(spawnEntityType.toUpperCase()) &&
							e.getClickedBlock() != null && this.wg.wg(p.getWorld(), e.getClickedBlock().getLocation(), false)) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyHangingBreakByEntity(final HangingBreakByEntityEvent e) {
		Entity entity = e.getEntity();
		Entity attacker = e.getRemover();
		final Location loc = entity.getLocation();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			assert attacker != null;
			if(RSApi.isAuthListenerPermission(attacker, IUtilPermissions.regionProtect, null))return;{
				if (Objects.requireNonNull(e.getRemover()).getType() == EntityType.PLAYER) {
					e.setCancelled(true);
				}
				if (entity instanceof ItemFrame
						|| entity instanceof GlowItemFrame
						|| entity instanceof Painting) {
					if (attacker instanceof Player) {
						e.setCancelled(true);
					}else if (attacker instanceof Projectile){
						attacker = ((Projectile) attacker).getShooter() instanceof Entity ? (Entity) ((Projectile) attacker).getShooter() : null;
						if (attacker instanceof Player) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyHangingPlace(final HangingPlaceEvent e) {
		final Entity entity = e.getEntity();
		final Player p = (Player) e.getPlayer();
		final Location loc = entity.getLocation();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			assert p != null;
			if(RSApi.isAuthListenerPermission(p, IUtilPermissions.regionProtect, null))return;{
				if (p.getInventory().getItemInMainHand().getType() == Material.ITEM_FRAME
						|| p.getInventory().getItemInMainHand().getType() == Material.GLOW_ITEM_FRAME
						|| p.getInventory().getItemInMainHand().getType() == Material.PAINTING) {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	private void denyExplode(final EntityExplodeEvent e) {
		final Entity entity = e.getEntity();
		final Location loc = entity.getLocation();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(e.getEntityType() == EntityType.PRIMED_TNT 
					|| e.getEntityType() == EntityType.ENDER_CRYSTAL 
					|| e.getEntityType() == EntityType.MINECART_TNT) {e.blockList().clear();}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void denyExplodeRespawnAnchor(final BlockExplodeEvent e) {
		final Block b = e.getBlock();
		final Location loc = b.getLocation();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(b.getType() == null)return;{
				if(b.getType() == Material.RESPAWN_ANCHOR) return;{
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
    private void denyUseWEAndWGCommand(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
		final Location loc = p.getLocation();
		final String[] s = e.getMessage().toLowerCase().split(" ");
		final String cmd = e.getMessage().split(" ")[0].toLowerCase();
		final String playerName = p.getName();
		if(RSApi.isAuthListenerPermission(p, IUtilPermissions.regionProtect, null))return;{
			if (this.cmdWE(s[0]) && !this.wg.checkIntersection(p)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtectWe) {
					p.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsgWe);
				}
				RSApi.notifyAdmin(p, playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
				RSApi.notifyConsole(playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
			}if (this.cmdWE_C(s[0]) && !this.wg.checkCIntersection(p, s)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtectWe) {
					p.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsgWe);
				}
				RSApi.notifyAdmin(p, playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
				RSApi.notifyConsole(playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
			}if (this.cmdWE_P(s[0]) && !this.wg.checkPIntersection(p, s)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtectWe) {
					p.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsgWe);
				}
				RSApi.notifyAdmin(p, playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
				RSApi.notifyConsole(playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
			}if (this.cmdWE_S(s[0]) && !this.wg.checkSIntersection(p, s)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtectWe) {
					p.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsgWe);
				}
				RSApi.notifyAdmin(p, playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
				RSApi.notifyConsole(playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
			}if (this.cmdWE_U(s[0]) && !this.wg.checkUIntersection(p, s)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtectWe) {
					p.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsgWe);
				}
				RSApi.notifyAdmin(p, playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
				RSApi.notifyConsole(playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
			}if (this.cmdWE_CP(s[0])) {
				e.setMessage(e.getMessage().replace("-o", ""));
				if (!this.wg.checkCPIntersection(p, s)) {
					e.setCancelled(true);
				}
				RSApi.notifyAdmin(p, playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
				RSApi.notifyConsole(playerName, cmd, String.valueOf(RSApi.getProtectRegionName(loc)));
			}
			if (this.regionCommandNameArgs.contains(s[0]) && s.length > 2) {
				for (final String list : WorldGuardRegionProtect.utilConfig.regionProtect) {
					if (list.equalsIgnoreCase(s[2])) {
						e.setCancelled(true);
					}
				}for (final String list : WorldGuardRegionProtect.utilConfig.regionProtectOnlyBreakAllow) {
					if (list.equalsIgnoreCase(s[2])) {
						e.setCancelled(true);
					}
				}
				if (s.length > 3 && this.regionEditArgs.contains(s[2].toLowerCase())
						|| s.length > 3 && this.regionEditArgsFlags.contains(s[2].toLowerCase())) {
					for (final String list : WorldGuardRegionProtect.utilConfig.regionProtect) {
						if (list.equalsIgnoreCase(s[3])) {
							e.setCancelled(true);
						}
					}for (final String list : WorldGuardRegionProtect.utilConfig.regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[3])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 4 && s[2].equalsIgnoreCase("-w")
						|| s.length > 4 && this.regionEditArgsFlags.contains(s[2].toLowerCase())) {
					for (final String list : WorldGuardRegionProtect.utilConfig.regionProtect) {
						if (list.equalsIgnoreCase(s[4])) {
							e.setCancelled(true);
						}
					}for (final String list : WorldGuardRegionProtect.utilConfig.regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[4])) {e.setCancelled(true);}
					}
				}
				if (s.length > 5 && s[3].equalsIgnoreCase("-w")
						|| s.length > 5 && this.regionEditArgs.contains(s[4].toLowerCase())
						|| s.length > 5 && this.regionEditArgsFlags.contains(s[4].toLowerCase())) {
					for (final String list : WorldGuardRegionProtect.utilConfig.regionProtect) {
						if (list.equalsIgnoreCase(s[5])) {e.setCancelled(true);}
					}for (final String list : WorldGuardRegionProtect.utilConfig.regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[5])) {e.setCancelled(true);}
					}
				}
				if (s.length > 6 && s[4].equalsIgnoreCase("-w")
						|| s.length > 6 && s[4].equalsIgnoreCase("-h")
						|| s.length > 6 && this.regionEditArgs.contains(s[5].toLowerCase())
						|| s.length > 6 && this.regionEditArgsFlags.contains(s[5].toLowerCase())) {
					for (final String list : WorldGuardRegionProtect.utilConfig.regionProtect) {
						if (list.equalsIgnoreCase(s[6])) {e.setCancelled(true);}
					}for (final String list : WorldGuardRegionProtect.utilConfig.regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[6])) {e.setCancelled(true);}
					}
				}
			}
		}
	}
	private Iwg setUpWorldGuardVersionSeven() {
		try{
			Class.forName("com.sk89q.worldedit.math.BlockVector3");
			return new wg7(this.worldGuardRegionProtect);
		}catch(ClassNotFoundException ignored){}return wg;
	}
	private boolean cmdWE(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : WorldGuardRegionProtect.utilConfig.cmdWe) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
	private boolean cmdWE_C(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : WorldGuardRegionProtect.utilConfig.cmdWeC) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
	private boolean cmdWE_P(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : WorldGuardRegionProtect.utilConfig.cmdWeP) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
	private boolean cmdWE_S(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : WorldGuardRegionProtect.utilConfig.cmdWeS) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
	private boolean cmdWE_U(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : WorldGuardRegionProtect.utilConfig.cmdWeU) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
	private boolean cmdWE_CP(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : WorldGuardRegionProtect.utilConfig.cmdWeCP) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
}