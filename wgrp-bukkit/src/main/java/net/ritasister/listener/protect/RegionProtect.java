package net.ritasister.listener.protect;

import net.ritasister.rslibs.api.RSApi;
import net.ritasister.rslibs.permissions.IUtilPermissions;
import net.ritasister.rslibs.util.wg.Iwg;
import net.ritasister.util.wg.wg7;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
		final Player player = e.getPlayer();
		final Player admin = e.getPlayer();
		final String playerName = e.getPlayer().getPlayerProfile().getName();
		final Block b = e.getBlock();
		final Location loc = b.getLocation();
		final String regionName = RSApi.getProtectRegionName(loc);
		double x = b.getX();
		double y = b.getY();
		double z = b.getZ();
		String world = b.getWorld().getName();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtectAllow)
			|| RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtectOnlyBreakAllow)) {
			e.setCancelled(false);
		} else if (RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			final Player p = e.getPlayer();
			if (!RSApi.isSenderListenerPermission(p, IUtilPermissions.regionProtect, null)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtect) {
					p.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsg);
				}
			}
		} else if(RSApi.checkStandingRegion(loc)){
			if(RSApi.isSenderListenerPermission(admin, IUtilPermissions.spyAdminForSuspect, null)) {
				RSApi.notifyIfBreakInRegion(admin, player, playerName, RSApi.getTime(), regionName, x, y, z, world);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	private void denyPlace(final BlockPlaceEvent e) {
		final Player player = e.getPlayer();
		final String playerName = player.getPlayerProfile().getName();
		final Block b = e.getBlock();
		final Location loc = b.getLocation();
		final String regionName = RSApi.getProtectRegionName(loc);
		double x = b.getX();
		double y = b.getY();
		double z = b.getZ();
		String world = b.getWorld().getName();
		if (RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtectAllow)) {
			e.setCancelled(false);
		} else if (RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)
				|| RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtectAllow)) {
			final Player p = e.getPlayer();
			if (RSApi.isSenderListenerPermission(p, IUtilPermissions.regionProtect, null)) return;{
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtect) {
					p.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsg);
				}
			}
		} else if(RSApi.checkStandingRegion(loc)){
			if(RSApi.isSenderListenerPermission(player, IUtilPermissions.spyAdminForSuspect, null)) {
				RSApi.notifyIfPlaceInRegion(player, player, playerName, RSApi.getTime(), regionName, x, y, z, world);
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyUseBucket(final PlayerBucketEmptyEvent e) {
		final Player p = e.getPlayer();
		final Location loc = e.getBlockClicked().getLocation();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(RSApi.isSenderListenerPermission(p, IUtilPermissions.regionProtect, WorldGuardRegionProtect.utilConfigMessage.wgrpMsg))return;{
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
			if(RSApi.isSenderListenerPermission(attacker, IUtilPermissions.regionProtect, null))return;{
				if (entity instanceof ArmorStand
						|| entity instanceof ItemFrame
						|| entity instanceof Painting
						|| entity instanceof EnderCrystal) {
					if (attacker instanceof Player
							|| attacker instanceof Creeper
							|| attacker instanceof Wither
							|| attacker instanceof Ghast) {
						e.setCancelled(true);
					}else if (attacker instanceof Projectile){
						attacker = ((Projectile) attacker).getShooter() instanceof Entity ? (Entity) ((Projectile) attacker).getShooter() : null;
						if (attacker instanceof Player
								|| attacker instanceof Creeper
								|| attacker instanceof Wither
								|| attacker instanceof Ghast) {
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
			if(RSApi.isSenderListenerPermission(player, IUtilPermissions.regionProtect, null))return;{
				if(e.getRightClicked().getType() == EntityType.ITEM_FRAME
						|| e.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME
						&& this.wg.wg(player.getWorld(), player.getLocation())) {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyManipulateArmorStand(final PlayerArmorStandManipulateEvent e) {
		final Player p = e.getPlayer();
		final Location clickLoc = e.getRightClicked().getLocation();
		if(RSApi.checkStandingRegion(clickLoc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(RSApi.isSenderListenerPermission(p, IUtilPermissions.regionProtect, null))return;{
				if(e.getRightClicked().getType() == EntityType.ARMOR_STAND 
						&& this.wg.wg(p.getWorld(), clickLoc)) {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyStructureGrow(final StructureGrowEvent e) {
		if (this.wg.wg(e.getWorld(), e.getLocation())) {e.setCancelled(true);}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyInteract(final PlayerInteractEvent e) {
		if (e.getItem() != null) {
			Player player = e.getPlayer();
			if(RSApi.isSenderListenerPermission(player, IUtilPermissions.regionProtect, null))return;{
				for(String spawnEntityType : WorldGuardRegionProtect.utilConfig.interactType) {
					if(e.getItem().getType() == Material.getMaterial(spawnEntityType.toUpperCase())
							&& e.getClickedBlock() != null
							&& this.wg.wg(player.getWorld(), e.getClickedBlock().getLocation())) {
						e.setCancelled(true);
					}else if(player.getInventory().getItemInMainHand().getType() == Material.GLOWSTONE
							&& e.getClickedBlock() != null
							&& e.getClickedBlock().getType() == Material.RESPAWN_ANCHOR
							&& this.wg.wg(player.getWorld(), e.getClickedBlock().getLocation())) {
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
			if(RSApi.isSenderListenerPermission(attacker, IUtilPermissions.regionProtect, null))return;{
				if (Objects.requireNonNull(e.getRemover()).getType() == EntityType.PLAYER) {
					e.setCancelled(true);
				}
				if (entity instanceof ItemFrame
						|| entity instanceof Painting) {
					if (attacker instanceof Player
						|| attacker instanceof Creeper
							|| attacker instanceof WitherSkull
							|| attacker instanceof Fireball) {
						e.setCancelled(true);
					}else if (attacker instanceof Projectile){
						attacker = ((Projectile) attacker).getShooter() instanceof Entity ? (Entity) ((Projectile) attacker).getShooter() : null;
						if (attacker instanceof Player
								|| attacker instanceof Creeper
								|| attacker instanceof WitherSkull
								|| attacker instanceof Fireball) {
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
		final Player player = e.getPlayer();
		final Location loc = entity.getLocation();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(RSApi.isSenderListenerPermission(player, IUtilPermissions.regionProtect, null))return;{
				if (player.getInventory().getItemInMainHand().getType() == Material.ITEM_FRAME
						|| player.getInventory().getItemInMainHand().getType() == Material.GLOW_ITEM_FRAME
						|| player.getInventory().getItemInMainHand().getType() == Material.PAINTING) {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority=EventPriority.LOWEST)
	private void denyExplode(final EntityExplodeEvent e) {
		final Entity entity = e.getEntity();
		final Location loc = entity.getLocation();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(e.getEntityType() == EntityType.PRIMED_TNT 
					|| e.getEntityType() == EntityType.ENDER_CRYSTAL 
					|| e.getEntityType() == EntityType.MINECART_TNT
					|| e.getEntityType() == EntityType.CREEPER) {
				e.blockList().clear();
			}
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void denyExplodeRespawnAnchor(final BlockExplodeEvent e) {
		final Block block = e.getBlock();
		final Location loc = block.getLocation();
		if(RSApi.checkStandingRegion(loc, WorldGuardRegionProtect.utilConfig.regionProtect)) {
			if(block.getType() == Material.RESPAWN_ANCHOR &&
					block != null && this.wg.wg(block.getWorld(), loc))return; {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
    private void denyUseWEAndWGCommand(final PlayerCommandPreprocessEvent e) {
        final Player player = e.getPlayer();
		final Location loc = player.getLocation();
		final String[] s = e.getMessage().toLowerCase().split(" ");
		final String cmd = e.getMessage().split(" ")[0].toLowerCase();
		final String playerName = player.getName();
		if(RSApi.isSenderListenerPermission(player, IUtilPermissions.regionProtect, null))return;{
			if (this.cmdWE(s[0]) && !this.wg.checkIntersection(player)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtectWe) {
					player.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsgWe);
				}
				RSApi.notify(player, playerName, cmd, RSApi.getProtectRegionName(loc));
				RSApi.notify(playerName, cmd, RSApi.getProtectRegionName(loc));
			}if (this.cmdWE_C(s[0]) && !this.wg.checkCIntersection(player, s)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtectWe) {
					player.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsgWe);
				}
				RSApi.notify(player, playerName, cmd, RSApi.getProtectRegionName(loc));
				RSApi.notify(playerName, cmd, RSApi.getProtectRegionName(loc));
			}if (this.cmdWE_P(s[0]) && !this.wg.checkPIntersection(player, s)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtectWe) {
					player.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsgWe);
				}
				RSApi.notify(player, playerName, cmd, RSApi.getProtectRegionName(loc));
				RSApi.notify(playerName, cmd, RSApi.getProtectRegionName(loc));
			}if (this.cmdWE_S(s[0]) && !this.wg.checkSIntersection(player, s)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtectWe) {
					player.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsgWe);
				}
				RSApi.notify(player, playerName, cmd, RSApi.getProtectRegionName(loc));
				RSApi.notify(playerName, cmd, RSApi.getProtectRegionName(loc));
			}if (this.cmdWE_U(s[0]) && !this.wg.checkUIntersection(player, s)) {
				e.setCancelled(true);
				if (WorldGuardRegionProtect.utilConfig.regionMessageProtectWe) {
					player.sendMessage(WorldGuardRegionProtect.utilConfigMessage.wgrpMsgWe);
				}
				RSApi.notify(player, playerName, cmd, RSApi.getProtectRegionName(loc));
				RSApi.notify(playerName, cmd, RSApi.getProtectRegionName(loc));
			}if (this.cmdWE_CP(s[0])) {
				e.setMessage(e.getMessage().replace("-o", ""));
				if (!this.wg.checkCPIntersection(player, s)) {
					e.setCancelled(true);
				}
				RSApi.notify(player, playerName, cmd, RSApi.getProtectRegionName(loc));
				RSApi.notify(playerName, cmd, RSApi.getProtectRegionName(loc));
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
						if (list.equalsIgnoreCase(s[4])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 5 && s[3].equalsIgnoreCase("-w")
						|| s.length > 5 && this.regionEditArgs.contains(s[4].toLowerCase())
						|| s.length > 5 && this.regionEditArgsFlags.contains(s[4].toLowerCase())) {
					for (final String list : WorldGuardRegionProtect.utilConfig.regionProtect) {
						if (list.equalsIgnoreCase(s[5])) {
							e.setCancelled(true);
						}
					}for (final String list : WorldGuardRegionProtect.utilConfig.regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[5])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 6 && s[4].equalsIgnoreCase("-w")
						|| s.length > 6 && s[4].equalsIgnoreCase("-h")
						|| s.length > 6 && this.regionEditArgs.contains(s[5].toLowerCase())
						|| s.length > 6 && this.regionEditArgsFlags.contains(s[5].toLowerCase())) {
					for (final String list : WorldGuardRegionProtect.utilConfig.regionProtect) {
						if (list.equalsIgnoreCase(s[6])) {
							e.setCancelled(true);
						}
					}for (final String list : WorldGuardRegionProtect.utilConfig.regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[6])) {
						}
					}
				}
			}
		}
	}
	private Iwg setUpWorldGuardVersionSeven() {
		try{
			Class.forName("com.sk89q.worldedit.math.BlockVector3");
			return new wg7(this.worldGuardRegionProtect);
		}catch(ClassNotFoundException ignored){}
		return wg;
	}
	private boolean cmdWE(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : WorldGuardRegionProtect.utilConfig.cmdWe) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}
		return false;
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