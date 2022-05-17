package net.ritasister.listener.protect;

import net.ritasister.rslibs.api.Action;
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

/**
 * @author RitaWolf
 *
 * Patched by itzRedTea
 *
 *
 */
public class RegionProtect implements Listener {

	private final WorldGuardRegionProtect pl;
	private final Iwg Iwg;
	private final List<String> regionCommandNameArgs = Arrays.asList(
			"/rg", "/region", "/regions",
			"/worldguard:rg", "/worldguard:region", "/worldguard:regions");
	private final List<String> regionEditArgs = Arrays.asList("f", "flag");
	private final List<String> regionEditArgsFlags = Arrays.asList("-f", "-u", "-n", "-g", "-a");

	public RegionProtect(WorldGuardRegionProtect worldGuardRegionProtect) {
		this.pl =worldGuardRegionProtect;
		this.Iwg=this.setUpWorldGuardVersionSeven();
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
		String regionName = pl.getRsRegion().getProtectRegion(loc);

		if(pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtectAllow)
				|| pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtectOnlyBreakAllow)) {
			e.setCancelled(false);
		} else if (pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtect)
				&& !pl.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) {
			e.setCancelled(true);
			if (pl.getUtilConfig().regionMessageProtect) {
				player.sendMessage(pl.getUtilConfigMessage().wgrpMsg);
			}
		} else if (pl.getRsRegion().checkStandingRegion(loc)
				&& pl.getRsApi().isSenderListenerPermission(player, IUtilPermissions.SPY_INSPECT_ADMIN_LISTENER, null)) {
			if(pl.spyLog.contains(uniqueId)) {
				pl.getRsApi().notifyIfActionInRegion(
						player, player, pl.getRsApi().getTime(),
						playerName, Action.BREAK, regionName,
						x, y, z, world);
			}
			if(pl.getUtilConfig().databaseEnable) {
				pl.getRsStorage().getDataSource().setLogAction(
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
		String regionName = pl.getRsRegion().getProtectRegion(loc);
		if(pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtectAllow)) {
			e.setCancelled(false);
		} else if (pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtect)
				&& !pl.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) {
			e.setCancelled(true);
			if (pl.getUtilConfig().regionMessageProtect) {
				player.sendMessage(pl.getUtilConfigMessage().wgrpMsg);
			}
		} else if (pl.getRsRegion().checkStandingRegion(loc)
				&& pl.getRsApi().isSenderListenerPermission(player, IUtilPermissions.SPY_INSPECT_ADMIN_LISTENER, null)) {
			if(pl.spyLog.contains(uniqueId)) {
				pl.getRsApi().notifyIfActionInRegion(
						player, player, pl.getRsApi().getTime(),
						playerName, Action.PLACE, regionName,
						x, y, z, world);
			}
			if(pl.getUtilConfig().databaseEnable) {
				pl.getRsStorage().getDataSource().setLogAction(
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
		if(pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtect)) {
			if(pl.getRsApi().isSenderListenerPermission(p, IUtilPermissions.REGION_PROTECT, pl.getUtilConfigMessage().wgrpMsg))return;{
				if (e.getBlockClicked().getType() == Material.LAVA_BUCKET
						|| e.getBlockClicked().getType() == Material.WATER_BUCKET
						|| e.getBlockClicked().getType() == Material.BUCKET) {
					e.setCancelled(true);
				}e.setCancelled(true);
			}
		}
	}

	/**
	 *
	 * @author itzRedTea
	 * NotTested, improved
	 * TODO - Test this.
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	private void denyEntityDamageByEntityEvent(final @NotNull EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		Entity attacker = e.getDamager();
		final Location loc = entity.getLocation();
		if(pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtect) && !pl.getRsApi().isSenderListenerPermission(attacker,
				IUtilPermissions.REGION_PROTECT, null)) {
			switch (entity.getType()) { case ARMOR_STAND, ITEM_FRAME, PAINTING, ENDER_CRYSTAL ->
			{
				switch (attacker.getType()) {
					case PLAYER, CREEPER, WITHER, WITHER_SKELETON, GHAST, SNOWMAN, SHULKER_BULLET, DROWNED,
							SKELETON, STRAY -> e.setCancelled(true);
					default -> {
						if(attacker instanceof Projectile) {
							attacker = ((Projectile) attacker).getShooter() instanceof Entity
									? (Entity) ((Projectile) attacker).getShooter() : null;
							switch (attacker.getType()) { case PLAYER, CREEPER, WITHER, WITHER_SKELETON, GHAST, SNOWMAN,
									SHULKER_BULLET, DROWNED, SKELETON, STRAY -> e.setCancelled(true);
							}
						}
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
		if (pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtect)) {
			assert attacker != null;
			if (pl.getRsApi().isSenderListenerPermission(attacker, IUtilPermissions.REGION_PROTECT, null)) return; {
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
		if(pl.getRsRegion().checkStandingRegion(clickLoc, pl.getUtilConfig().regionProtect)) {
			if(pl.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
				if(e.getRightClicked().getType() == EntityType.ITEM_FRAME
						|| e.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME
						&& this.Iwg.wg(player.getWorld(), player.getLocation())) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyManipulateArmorStand(final @NotNull PlayerArmorStandManipulateEvent e) {
		final Player p = e.getPlayer();
		final Location clickLoc = e.getRightClicked().getLocation();
		if(pl.getRsRegion().checkStandingRegion(clickLoc, pl.getUtilConfig().regionProtect)) {
			if(pl.getRsApi().isSenderListenerPermission(p, IUtilPermissions.REGION_PROTECT, null))return;{
				if(e.getRightClicked().getType() == EntityType.ARMOR_STAND && this.Iwg.wg(p.getWorld(), clickLoc)) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyStructureGrow(final @NotNull StructureGrowEvent e) {
		if (this.Iwg.wg(e.getWorld(), e.getLocation())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyInteract(final @NotNull PlayerInteractEvent e) {
		if (e.getItem() != null) {
			Player player = e.getPlayer();
			org.bukkit.event.block.Action action = e.getAction();
			if(pl.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;
			{
				for (String spawnEntityType : pl.getUtilConfig().interactType) {
					if (e.getItem().getType() == Material.getMaterial(spawnEntityType.toUpperCase())
							&& e.getClickedBlock() != null
							&& this.Iwg.wg(player.getWorld(), e.getClickedBlock().getLocation())) {
						e.setCancelled(true);
					} else if (player.getInventory().getItemInMainHand().getType() == Material.GLOWSTONE
							&& e.getClickedBlock() != null
							&& e.getClickedBlock().getType() == Material.RESPAWN_ANCHOR
							&& this.Iwg.wg(player.getWorld(), e.getClickedBlock().getLocation())) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyHangingPlace(final @NotNull HangingPlaceEvent e) {
		final Entity entity = e.getEntity();
		final Player player = e.getPlayer();
		final Location loc = entity.getLocation();
		if (pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtect)) {
			assert player != null;
			if (pl.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) return;
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
		if (pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtect)) {
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
		if (pl.getRsRegion().checkStandingRegion(loc, pl.getUtilConfig().regionProtect)) {
			if (block.getType() == Material.RESPAWN_ANCHOR && this.Iwg.wg(block.getWorld(), loc))return; {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyUseWEAndIwgCommand(final @NotNull PlayerCommandPreprocessEvent e) {
		final Player player = e.getPlayer();
		final String playerName = player.getName();
		final Location loc = player.getLocation();
		final String[] s = e.getMessage().toLowerCase().split(" ");
		final String cmd = e.getMessage().split(" ")[0].toLowerCase();
		if(pl.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
			if (this.cmdWE(s[0]) && !this.Iwg.checkIntersection(player)) {
				e.setCancelled(true);
				if (pl.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(pl.getUtilConfigMessage().wgrpMsgWe);
				}
				pl.getRsApi().notify(player, playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
				pl.getRsApi().notify(playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_C(s[0]) && !this.Iwg.checkCIntersection(player, s)) {
				e.setCancelled(true);
				if (pl.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(pl.getUtilConfigMessage().wgrpMsgWe);
				}
				pl.getRsApi().notify(player, playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
				pl.getRsApi().notify(playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_P(s[0]) && !this.Iwg.checkPIntersection(player, s)) {
				e.setCancelled(true);
				if (pl.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(pl.getUtilConfigMessage().wgrpMsgWe);
				}
				pl.getRsApi().notify(player, playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
				pl.getRsApi().notify(playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_S(s[0]) && !this.Iwg.checkSIntersection(player, s)) {
				e.setCancelled(true);
				if (pl.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(pl.getUtilConfigMessage().wgrpMsgWe);
				}
				pl.getRsApi().notify(player, playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
				pl.getRsApi().notify(playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_U(s[0]) && !this.Iwg.checkUIntersection(player, s)) {
				e.setCancelled(true);
				if (pl.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(pl.getUtilConfigMessage().wgrpMsgWe);
				}
				pl.getRsApi().notify(player, playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
				pl.getRsApi().notify(playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_CP(s[0])) {
				e.setMessage(e.getMessage().replace("-o", ""));
				if (!this.Iwg.checkCPIntersection(player, s)) {
					e.setCancelled(true);
				}
				pl.getRsApi().notify(player, playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
				pl.getRsApi().notify(playerName, cmd, pl.getRsApi().getProtectRegionName(loc));
			}
			if (this.regionCommandNameArgs.contains(s[0]) && s.length > 2) {
				for (final String list : pl.getUtilConfig().regionProtect) {
					if (list.equalsIgnoreCase(s[2])) {
						e.setCancelled(true);
					}
				}for (final String list : pl.getUtilConfig().regionProtectOnlyBreakAllow) {
					if (list.equalsIgnoreCase(s[2])) {
						e.setCancelled(true);
					}
				}
				if (s.length > 3 && this.regionEditArgs.contains(s[2].toLowerCase())
						|| s.length > 3 && this.regionEditArgsFlags.contains(s[2].toLowerCase())) {
					for (final String list : pl.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[3])) {
							e.setCancelled(true);
						}
					}for (final String list : pl.getUtilConfig().regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[3])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 4 && s[2].equalsIgnoreCase("-w")
						|| s.length > 4 && this.regionEditArgsFlags.contains(s[2].toLowerCase())) {
					for (final String list : pl.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[4])) {
							e.setCancelled(true);
						}
					}for (final String list : pl.getUtilConfig().regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[4])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 5 && s[3].equalsIgnoreCase("-w")
						|| s.length > 5 && this.regionEditArgs.contains(s[4].toLowerCase())
						|| s.length > 5 && this.regionEditArgsFlags.contains(s[4].toLowerCase())) {
					for (final String list : pl.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[5])) {
							e.setCancelled(true);
						}
					}for (final String list : pl.getUtilConfig().regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[5])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 6 && s[4].equalsIgnoreCase("-w")
						|| s.length > 6 && s[4].equalsIgnoreCase("-h")
						|| s.length > 6 && this.regionEditArgs.contains(s[5].toLowerCase())
						|| s.length > 6 && this.regionEditArgsFlags.contains(s[5].toLowerCase())) {
					for (final String list : pl.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[6])) {
							e.setCancelled(true);
						}
					}
					for (final String list : pl.getUtilConfig().regionProtectOnlyBreakAllow) {
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
			return new wg7(this.pl);
		} catch (ClassNotFoundException ignored) {
		}
		return Iwg;
	}

	private boolean cmdWE(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : pl.getUtilConfig().cmdWe) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	private boolean cmdWE_C(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : pl.getUtilConfig().cmdWeC) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}

	private boolean cmdWE_P(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : pl.getUtilConfig().cmdWeP) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}

	private boolean cmdWE_S(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : pl.getUtilConfig().cmdWeS) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}

	private boolean cmdWE_U(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : pl.getUtilConfig().cmdWeU) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}

	private boolean cmdWE_CP(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : pl.getUtilConfig().cmdWeCP) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
}