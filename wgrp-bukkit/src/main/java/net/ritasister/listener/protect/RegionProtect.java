package net.ritasister.listener.protect;

import net.ritasister.rslibs.api.Action;
import net.ritasister.rslibs.api.RSApi;
import net.ritasister.rslibs.api.RSRegion;
import net.ritasister.rslibs.permissions.IUtilPermissions;
import net.ritasister.rslibs.util.wg.Iwg;
import net.ritasister.util.wg.wg7;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
	private void denyBreak(final @NotNull BlockBreakEvent e) {
		final Player player = e.getPlayer();
		final String playerName = e.getPlayer().getPlayerProfile().getName();
		final UUID uniqueId = player.getUniqueId();
		final Block b = e.getBlock();
		final Location loc = b.getLocation();
		double x = b.getX();
		double y = b.getY();
		double z = b.getZ();
		String world = b.getWorld().getName();
		String regionName = worldGuardRegionProtect.getRsRegion().getProtectRegion(loc);
		if(worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtectAllow)
				|| worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow)) {
			e.setCancelled(false);
		} else if (worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtect)
				&& !worldGuardRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) {
			e.setCancelled(true);
			if (worldGuardRegionProtect.getUtilConfig().regionMessageProtect) {
				player.sendMessage(worldGuardRegionProtect.getUtilConfigMessage().wgrpMsg);
			}
		} else if (worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc) && worldGuardRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.SPY_ADMIN_FOR_SUSPECT, null)) {
			worldGuardRegionProtect.getRsApi().notifyIfActionInRegion(
					player, player, worldGuardRegionProtect.getRsApi().getTime(),
					playerName, Action.BREAK, regionName,
					x, y, z, world);
			if(worldGuardRegionProtect.getUtilConfig().databaseEnable) {
				worldGuardRegionProtect.getRsStorage().getDataSource().setLogAction(
						playerName, uniqueId,
						System.currentTimeMillis(), Action.BREAK,
						regionName, b.getWorld().getName(), b.getX(),
						b.getY(), b.getZ());
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	private void denyPlace(final @NotNull BlockPlaceEvent e) {
		final Player player = e.getPlayer();
		final String playerName = e.getPlayer().getPlayerProfile().getName();
		final UUID uniqueId = player.getUniqueId();
		final Block b = e.getBlock();
		final Location loc = b.getLocation();
		double x = b.getX();
		double y = b.getY();
		double z = b.getZ();
		String world = b.getWorld().getName();
		String regionName = worldGuardRegionProtect.getRsRegion().getProtectRegion(loc);
		if(worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtectAllow)) {
			e.setCancelled(false);
		} else if (worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtect)
				&& !worldGuardRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) {
			e.setCancelled(true);
			if (worldGuardRegionProtect.getUtilConfig().regionMessageProtect) {
				player.sendMessage(worldGuardRegionProtect.getUtilConfigMessage().wgrpMsg);
			}
		} else if (worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc) && worldGuardRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.SPY_ADMIN_FOR_SUSPECT, null)) {
			worldGuardRegionProtect.getRsApi().notifyIfActionInRegion(
					player, player, worldGuardRegionProtect.getRsApi().getTime(),
					playerName, Action.PLACE, regionName,
					x, y, z, world);
			if(worldGuardRegionProtect.getUtilConfig().databaseEnable) {
				worldGuardRegionProtect.getRsStorage().getDataSource().setLogAction(
						playerName,
						uniqueId,
						System.currentTimeMillis(),
						Action.PLACE,
						regionName,
						b.getWorld().getName(),
						b.getX(),
						b.getY(),
						b.getZ());
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyUseBucket(final @NotNull PlayerBucketEmptyEvent e) {
		final Player p = e.getPlayer();
		final Location loc = e.getBlockClicked().getLocation();
		if(worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtect)) {
			if(worldGuardRegionProtect.getRsApi().isSenderListenerPermission(p, IUtilPermissions.REGION_PROTECT, worldGuardRegionProtect.getUtilConfigMessage().wgrpMsg))return;{
				if (e.getBlockClicked().getType() == Material.LAVA_BUCKET
						|| e.getBlockClicked().getType() == Material.WATER_BUCKET
						|| e.getBlockClicked().getType() == Material.BUCKET) {
					e.setCancelled(true);
				}e.setCancelled(true);
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyEntityDamageByEntityEvent(final @NotNull EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		Entity attacker = e.getDamager();
		final Location loc = entity.getLocation();
		if(worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtect)) {
			if(worldGuardRegionProtect.getRsApi().isSenderListenerPermission(attacker, IUtilPermissions.REGION_PROTECT, null))return;{
				if (entity instanceof ArmorStand
						|| entity instanceof ItemFrame
						|| entity instanceof Painting
						|| entity instanceof EnderCrystal) {
					if (attacker instanceof Player || attacker instanceof Creeper
							|| attacker instanceof Wither | attacker instanceof WitherSkeleton
							|| attacker instanceof Ghast || attacker instanceof Snowman
							|| attacker instanceof ShulkerBullet || attacker instanceof Drowned
							|| attacker instanceof Skeleton || attacker instanceof Stray) {
						e.setCancelled(true);
					}else if (attacker instanceof Projectile){
						attacker = ((Projectile) attacker).getShooter() instanceof Entity
								? (Entity) ((Projectile) attacker).getShooter() : null;
						if (attacker instanceof Player || attacker instanceof Creeper
								|| attacker instanceof Wither | attacker instanceof WitherSkeleton
								|| attacker instanceof Ghast || attacker instanceof Snowman
								|| attacker instanceof ShulkerBullet || attacker instanceof Drowned
								|| attacker instanceof Skeleton || attacker instanceof Stray) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyHangingBreakByEntity(final @NotNull HangingBreakByEntityEvent e) {
		Entity entity = e.getEntity();
		Entity attacker = e.getRemover();
		final Location loc = entity.getLocation();
		if (worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtect)) {
			assert attacker != null;
			if (worldGuardRegionProtect.getRsApi().isSenderListenerPermission(attacker, IUtilPermissions.REGION_PROTECT, null)) return; {
				if (Objects.requireNonNull(e.getRemover()).getType() == EntityType.PLAYER) {
					e.setCancelled(true);
				}
				if (entity instanceof ItemFrame || entity instanceof Painting) {
					if (attacker instanceof Player || attacker instanceof Creeper
							|| attacker instanceof Wither | attacker instanceof WitherSkeleton
							|| attacker instanceof Ghast || attacker instanceof Snowman
							|| attacker instanceof ShulkerBullet || attacker instanceof Drowned
							|| attacker instanceof Skeleton || attacker instanceof Stray) {
						if (attacker instanceof Projectile) {
							attacker = ((Projectile) attacker).getShooter() instanceof Entity ? (Entity) ((Projectile) attacker).getShooter() : null;
							if (attacker instanceof Player || attacker instanceof Creeper
									|| attacker instanceof Wither | attacker instanceof WitherSkeleton
									|| attacker instanceof Ghast || attacker instanceof Snowman
									|| attacker instanceof ShulkerBullet || attacker instanceof Drowned
									|| attacker instanceof Skeleton || attacker instanceof Stray)  {
								e.setCancelled(true);
							}
						}
					} else {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyInteractWithEntity(final @NotNull PlayerInteractEntityEvent e) {
		final Player player = e.getPlayer();
		final Location clickLoc = e.getRightClicked().getLocation();
		if(worldGuardRegionProtect.getRsRegion().checkStandingRegion(clickLoc, worldGuardRegionProtect.getUtilConfig().regionProtect)) {
			if(worldGuardRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
				if(e.getRightClicked().getType() == EntityType.ITEM_FRAME
						|| e.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME
						&& this.wg.wg(player.getWorld(), player.getLocation())) {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyManipulateArmorStand(final @NotNull PlayerArmorStandManipulateEvent e) {
		final Player p = e.getPlayer();
		final Location clickLoc = e.getRightClicked().getLocation();
		if(worldGuardRegionProtect.getRsRegion().checkStandingRegion(clickLoc, worldGuardRegionProtect.getUtilConfig().regionProtect)) {
			if(worldGuardRegionProtect.getRsApi().isSenderListenerPermission(p, IUtilPermissions.REGION_PROTECT, null))return;{
				if(e.getRightClicked().getType() == EntityType.ARMOR_STAND && this.wg.wg(p.getWorld(), clickLoc)) {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyStructureGrow(final @NotNull StructureGrowEvent e) {
		if (this.wg.wg(e.getWorld(), e.getLocation())) {
			e.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyInteract(final @NotNull PlayerInteractEvent e) {
		if (e.getItem() != null) {
			Player player = e.getPlayer();
			if(worldGuardRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
				for(String spawnEntityType : worldGuardRegionProtect.getUtilConfig().interactType) {
					if(e.getItem().getType() == Material.getMaterial(spawnEntityType.toUpperCase())
							&& e.getClickedBlock() != null
							&& this.wg.wg(player.getWorld(), e.getClickedBlock().getLocation())) {
						e.setCancelled(true);
					}else if(e.getClickedBlock() != null
							&& e.getClickedBlock().getType() == Material.getMaterial(DoorsAndTrapDoors())
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
	private String DoorsAndTrapDoors() {
		@NotNull List<String> Doors = List.of("IRON_DOOR", "OAK_DOOR", "OAK_DOOR",
				"SPRUCE_DOOR", "BIRCH_DOOR", "JUNGLE_DOOR",
				"ACACIA_DOOR", "DARK_OAK_DOOR", "CRIMSON_DOOR",
				"WARPED_DOOR", "IRON_TRAPDOOR", "OAK_TRAPDOOR",
				"SPRUCE_TRAPDOOR", "BIRCH_TRAPDOOR", "JUNGLE_TRAPDOOR",
				"ACACIA_TRAPDOOR", "DARK_OAK_TRAPDOOR", "CRIMSON_TRAPDOOR",
				"WARPED_TRAPDOOR");
		return Doors.toString();
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyHangingPlace(final @NotNull HangingPlaceEvent e) {
		final Entity entity = e.getEntity();
		final Player player = e.getPlayer();
		final Location loc = entity.getLocation();
		if (worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtect)) {
			assert player != null;
			if (worldGuardRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) return;
			{
				if (player.getInventory().getItemInMainHand().getType() == Material.ITEM_FRAME
						|| player.getInventory().getItemInMainHand().getType() == Material.GLOW_ITEM_FRAME
						|| player.getInventory().getItemInMainHand().getType() == Material.PAINTING) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyExplode(final @NotNull EntityExplodeEvent e) {
		final Entity entity = e.getEntity();
		final Location loc = entity.getLocation();
		if (worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtect)) {
			if (e.getEntityType() == EntityType.PRIMED_TNT
					|| e.getEntityType() == EntityType.ENDER_CRYSTAL
					|| e.getEntityType() == EntityType.MINECART_TNT
					|| e.getEntityType() == EntityType.CREEPER
					|| e.getEntityType() == EntityType.FIREBALL
					|| e.getEntityType() == EntityType.WITHER_SKULL) {
				e.blockList().clear();
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyExplodeRespawnAnchor(final @NotNull BlockExplodeEvent e) {
		final Block block = e.getBlock();
		final Location loc = block.getLocation();
		if (worldGuardRegionProtect.getRsRegion().checkStandingRegion(loc, worldGuardRegionProtect.getUtilConfig().regionProtect)) {
			if (block.getType() == Material.RESPAWN_ANCHOR && this.wg.wg(block.getWorld(), loc))return; {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyUseWEAndWGCommand(final @NotNull PlayerCommandPreprocessEvent e) {
		final Player player = e.getPlayer();
		final String playerName = player.getName();
		final Location loc = player.getLocation();
		final String[] s = e.getMessage().toLowerCase().split(" ");
		final String cmd = e.getMessage().split(" ")[0].toLowerCase();
		if(worldGuardRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
			if (this.cmdWE(s[0]) && !this.wg.checkIntersection(player)) {
				e.setCancelled(true);
				if (worldGuardRegionProtect.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(worldGuardRegionProtect.getUtilConfigMessage().wgrpMsgWe);
				}
				worldGuardRegionProtect.getRsApi().notify(player, playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
				worldGuardRegionProtect.getRsApi().notify(playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_C(s[0]) && !this.wg.checkCIntersection(player, s)) {
				e.setCancelled(true);
				if (worldGuardRegionProtect.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(worldGuardRegionProtect.getUtilConfigMessage().wgrpMsgWe);
				}
				worldGuardRegionProtect.getRsApi().notify(player, playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
				worldGuardRegionProtect.getRsApi().notify(playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_P(s[0]) && !this.wg.checkPIntersection(player, s)) {
				e.setCancelled(true);
				if (worldGuardRegionProtect.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(worldGuardRegionProtect.getUtilConfigMessage().wgrpMsgWe);
				}
				worldGuardRegionProtect.getRsApi().notify(player, playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
				worldGuardRegionProtect.getRsApi().notify(playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_S(s[0]) && !this.wg.checkSIntersection(player, s)) {
				e.setCancelled(true);
				if (worldGuardRegionProtect.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(worldGuardRegionProtect.getUtilConfigMessage().wgrpMsgWe);
				}
				worldGuardRegionProtect.getRsApi().notify(player, playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
				worldGuardRegionProtect.getRsApi().notify(playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_U(s[0]) && !this.wg.checkUIntersection(player, s)) {
				e.setCancelled(true);
				if (worldGuardRegionProtect.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(worldGuardRegionProtect.getUtilConfigMessage().wgrpMsgWe);
				}
				worldGuardRegionProtect.getRsApi().notify(player, playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
				worldGuardRegionProtect.getRsApi().notify(playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_CP(s[0])) {
				e.setMessage(e.getMessage().replace("-o", ""));
				if (!this.wg.checkCPIntersection(player, s)) {
					e.setCancelled(true);
				}
				worldGuardRegionProtect.getRsApi().notify(player, playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
				worldGuardRegionProtect.getRsApi().notify(playerName, cmd, worldGuardRegionProtect.getRsApi().getProtectRegionName(loc));
			}
			if (this.regionCommandNameArgs.contains(s[0]) && s.length > 2) {
				for (final String list : worldGuardRegionProtect.getUtilConfig().regionProtect) {
					if (list.equalsIgnoreCase(s[2])) {
						e.setCancelled(true);
					}
				}for (final String list : worldGuardRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow) {
					if (list.equalsIgnoreCase(s[2])) {
						e.setCancelled(true);
					}
				}
				if (s.length > 3 && this.regionEditArgs.contains(s[2].toLowerCase())
						|| s.length > 3 && this.regionEditArgsFlags.contains(s[2].toLowerCase())) {
					for (final String list : worldGuardRegionProtect.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[3])) {
							e.setCancelled(true);
						}
					}for (final String list : worldGuardRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[3])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 4 && s[2].equalsIgnoreCase("-w")
						|| s.length > 4 && this.regionEditArgsFlags.contains(s[2].toLowerCase())) {
					for (final String list : worldGuardRegionProtect.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[4])) {
							e.setCancelled(true);
						}
					}for (final String list : worldGuardRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[4])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 5 && s[3].equalsIgnoreCase("-w")
						|| s.length > 5 && this.regionEditArgs.contains(s[4].toLowerCase())
						|| s.length > 5 && this.regionEditArgsFlags.contains(s[4].toLowerCase())) {
					for (final String list : worldGuardRegionProtect.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[5])) {
							e.setCancelled(true);
						}
					}for (final String list : worldGuardRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[5])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 6 && s[4].equalsIgnoreCase("-w")
						|| s.length > 6 && s[4].equalsIgnoreCase("-h")
						|| s.length > 6 && this.regionEditArgs.contains(s[5].toLowerCase())
						|| s.length > 6 && this.regionEditArgsFlags.contains(s[5].toLowerCase())) {
					for (final String list : worldGuardRegionProtect.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[6])) {
							e.setCancelled(true);
						}
					}
					for (final String list : worldGuardRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[6])) {
							e.setCancelled(false);
						}
					}
				}
			}
		}
	}
	private Iwg setUpWorldGuardVersionSeven() {
		try {
			Class.forName("com.sk89q.worldedit.math.BlockVector3");
			return new wg7(this.worldGuardRegionProtect);
		} catch (ClassNotFoundException ignored) {
		}
		return wg;
	}
	private boolean cmdWE(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : worldGuardRegionProtect.getUtilConfig().cmdWe) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	private boolean cmdWE_C(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : worldGuardRegionProtect.getUtilConfig().cmdWeC) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
	private boolean cmdWE_P(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : worldGuardRegionProtect.getUtilConfig().cmdWeP) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
	private boolean cmdWE_S(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : worldGuardRegionProtect.getUtilConfig().cmdWeS) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
	private boolean cmdWE_U(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : worldGuardRegionProtect.getUtilConfig().cmdWeU) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
	private boolean cmdWE_CP(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : worldGuardRegionProtect.getUtilConfig().cmdWeCP) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
}