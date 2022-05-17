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

public class RegionProtect implements Listener {

	private final WorldGuardRegionProtect wgRegionProtect;
	private final Iwg Iwg;
	private final List<String> regionCommandNameArgs = Arrays.asList(
			"/rg", "/region", "/regions",
			"/worldguard:rg", "/worldguard:region", "/worldguard:regions");
	private final List<String> regionEditArgs = Arrays.asList("f", "flag");
	private final List<String> regionEditArgsFlags = Arrays.asList("-f", "-u", "-n", "-g", "-a");

	public RegionProtect(WorldGuardRegionProtect worldGuardRegionProtect) {
		this.wgRegionProtect =worldGuardRegionProtect;
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
		String regionName = wgRegionProtect.getRsRegion().getProtectRegion(loc);
		if(wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtectAllow)
				|| wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow)) {
			e.setCancelled(false);
		} else if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtect)
				&& !wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) {
			e.setCancelled(true);
			if (wgRegionProtect.getUtilConfig().regionMessageProtect) {
				player.sendMessage(wgRegionProtect.getUtilConfigMessage().wgrpMsg);
			}
		} else if (wgRegionProtect.getRsRegion().checkStandingRegion(loc)
				&& wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.SPY_INSPECT_ADMIN_LISTENER, null)) {
			if(wgRegionProtect.spyLog.contains(uniqueId)) {
				wgRegionProtect.getRsApi().notifyIfActionInRegion(
						player, player, wgRegionProtect.getRsApi().getTime(),
						playerName, Action.BREAK, regionName,
						x, y, z, world);
			}
			if(wgRegionProtect.getUtilConfig().databaseEnable) {
				wgRegionProtect.getRsStorage().getDataSource().setLogAction(
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
		String regionName = wgRegionProtect.getRsRegion().getProtectRegion(loc);
		if(wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtectAllow)) {
			e.setCancelled(false);
		} else if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtect)
				&& !wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) {
			e.setCancelled(true);
			if (wgRegionProtect.getUtilConfig().regionMessageProtect) {
				player.sendMessage(wgRegionProtect.getUtilConfigMessage().wgrpMsg);
			}
		} else if (wgRegionProtect.getRsRegion().checkStandingRegion(loc)
				&& wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.SPY_INSPECT_ADMIN_LISTENER, null)) {
			if(wgRegionProtect.spyLog.contains(uniqueId)) {
				wgRegionProtect.getRsApi().notifyIfActionInRegion(
						player, player, wgRegionProtect.getRsApi().getTime(),
						playerName, Action.PLACE, regionName,
						x, y, z, world);
			}
			if(wgRegionProtect.getUtilConfig().databaseEnable) {
				wgRegionProtect.getRsStorage().getDataSource().setLogAction(
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
		final Material bucketType = e.getBlockClicked().getType();
		if(wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtect)) {
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(p, IUtilPermissions.REGION_PROTECT, wgRegionProtect.getUtilConfigMessage().wgrpMsg))return;{
					switch (bucketType) { case LAVA_BUCKET, WATER_BUCKET, BUCKET -> e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyEntityDamageByEntityEvent(final @NotNull EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		Entity attacker = e.getDamager();
		final Location loc = entity.getLocation();
		if(wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtect)) {
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(attacker, IUtilPermissions.REGION_PROTECT, null))return;{
				if (entity instanceof ArmorStand || entity instanceof ItemFrame
						|| entity instanceof Painting || entity instanceof EnderCrystal) {
					switch(attacker.getType()) {
						case PLAYER, CREEPER, ENDER_CRYSTAL, WITHER, WITHER_SKELETON, GHAST,
								SNOWMAN, SHULKER_BULLET, DROWNED, SKELETON, STRAY -> e.setCancelled(true);
						default -> {
							if(attacker instanceof Projectile) {
								attacker = ((Projectile) attacker).getShooter() instanceof Entity
										? (Entity) ((Projectile) attacker).getShooter() : null;
								switch (Objects.requireNonNull(attacker).getType()) {
									case PLAYER, CREEPER, ENDER_CRYSTAL, WITHER, WITHER_SKELETON, GHAST, SNOWMAN,
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
		if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtect)) {
			assert attacker != null;
			if (wgRegionProtect.getRsApi().isSenderListenerPermission(attacker, IUtilPermissions.REGION_PROTECT, null)) return; {
				if (entity instanceof ItemFrame || entity instanceof Painting) {
					switch(attacker.getType()) {
						case PLAYER, CREEPER, ENDER_CRYSTAL, WITHER, WITHER_SKELETON, GHAST,
								SNOWMAN, SHULKER_BULLET, DROWNED, SKELETON, STRAY -> e.setCancelled(true);
						default -> {
							if(attacker instanceof Projectile) {
								attacker = ((Projectile) attacker).getShooter() instanceof Entity
										? (Entity) ((Projectile) attacker).getShooter() : null;
								switch (Objects.requireNonNull(attacker).getType()) {
									case PLAYER, CREEPER, ENDER_CRYSTAL, WITHER, WITHER_SKELETON, GHAST, SNOWMAN,
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
	private void denyInteractWithEntity(final @NotNull PlayerInteractEntityEvent e) {
		final Player player = e.getPlayer();
		final Location clickLoc = e.getRightClicked().getLocation();
		if(wgRegionProtect.getRsRegion().checkStandingRegion(clickLoc, wgRegionProtect.getUtilConfig().regionProtect)) {
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
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
		if(wgRegionProtect.getRsRegion().checkStandingRegion(clickLoc, wgRegionProtect.getUtilConfig().regionProtect)) {
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(p, IUtilPermissions.REGION_PROTECT, null))return;{
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
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
				for (String spawnEntityType : wgRegionProtect.getUtilConfig().interactType) {
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
		if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtect)) {
			assert player != null;
			if (wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) return;{
				switch (player.getInventory().getItemInMainHand().getType()) {
					case ITEM_FRAME, GLOW_ITEM_FRAME, PAINTING -> e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyExplode(final @NotNull EntityExplodeEvent e) {
		final Entity entity = e.getEntity();
		final EntityType entityType = e.getEntityType();
		final Location loc = entity.getLocation();
		if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtect)) {
			switch (entityType) {
				case PRIMED_TNT, ENDER_CRYSTAL, MINECART_TNT, CREEPER, FIREBALL, WITHER_SKULL -> e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyExplodeRespawnAnchor(final @NotNull BlockExplodeEvent e) {
		final Block block = e.getBlock();
		final Location loc = block.getLocation();
		if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().regionProtect)) {
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
		if(wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
			if (this.cmdWE(s[0]) && !this.Iwg.checkIntersection(player)) {
				e.setCancelled(true);
				if (wgRegionProtect.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(wgRegionProtect.getUtilConfigMessage().wgrpMsgWe);
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_C(s[0]) && !this.Iwg.checkCIntersection(player, s)) {
				e.setCancelled(true);
				if (wgRegionProtect.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(wgRegionProtect.getUtilConfigMessage().wgrpMsgWe);
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_P(s[0]) && !this.Iwg.checkPIntersection(player, s)) {
				e.setCancelled(true);
				if (wgRegionProtect.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(wgRegionProtect.getUtilConfigMessage().wgrpMsgWe);
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_S(s[0]) && !this.Iwg.checkSIntersection(player, s)) {
				e.setCancelled(true);
				if (wgRegionProtect.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(wgRegionProtect.getUtilConfigMessage().wgrpMsgWe);
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_U(s[0]) && !this.Iwg.checkUIntersection(player, s)) {
				e.setCancelled(true);
				if (wgRegionProtect.getUtilConfig().regionMessageProtectWe) {
					player.sendMessage(wgRegionProtect.getUtilConfigMessage().wgrpMsgWe);
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
			}if (this.cmdWE_CP(s[0])) {
				e.setMessage(e.getMessage().replace("-o", ""));
				if (!this.Iwg.checkCPIntersection(player, s)) {
					e.setCancelled(true);
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsApi().getProtectRegionName(loc));
			}
			if (this.regionCommandNameArgs.contains(s[0]) && s.length > 2) {
				for (final String list : wgRegionProtect.getUtilConfig().regionProtect) {
					if (list.equalsIgnoreCase(s[2])) {
						e.setCancelled(true);
					}
				}for (final String list : wgRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow) {
					if (list.equalsIgnoreCase(s[2])) {
						e.setCancelled(true);
					}
				}
				if (s.length > 3 && this.regionEditArgs.contains(s[2].toLowerCase())
						|| s.length > 3 && this.regionEditArgsFlags.contains(s[2].toLowerCase())) {
					for (final String list : wgRegionProtect.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[3])) {
							e.setCancelled(true);
						}
					}for (final String list : wgRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[3])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 4 && s[2].equalsIgnoreCase("-w")
						|| s.length > 4 && this.regionEditArgsFlags.contains(s[2].toLowerCase())) {
					for (final String list : wgRegionProtect.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[4])) {
							e.setCancelled(true);
						}
					}for (final String list : wgRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[4])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 5 && s[3].equalsIgnoreCase("-w")
						|| s.length > 5 && this.regionEditArgs.contains(s[4].toLowerCase())
						|| s.length > 5 && this.regionEditArgsFlags.contains(s[4].toLowerCase())) {
					for (final String list : wgRegionProtect.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[5])) {
							e.setCancelled(true);
						}
					}for (final String list : wgRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow) {
						if (list.equalsIgnoreCase(s[5])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 6 && s[4].equalsIgnoreCase("-w")
						|| s.length > 6 && s[4].equalsIgnoreCase("-h")
						|| s.length > 6 && this.regionEditArgs.contains(s[5].toLowerCase())
						|| s.length > 6 && this.regionEditArgsFlags.contains(s[5].toLowerCase())) {
					for (final String list : wgRegionProtect.getUtilConfig().regionProtect) {
						if (list.equalsIgnoreCase(s[6])) {
							e.setCancelled(true);
						}
					}
					for (final String list : wgRegionProtect.getUtilConfig().regionProtectOnlyBreakAllow) {
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
			return new wg7(this.wgRegionProtect);
		} catch (ClassNotFoundException ignored) {
		}
		return Iwg;
	}

	private boolean cmdWE(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : wgRegionProtect.getUtilConfig().cmdWe) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	private boolean cmdWE_C(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : wgRegionProtect.getUtilConfig().cmdWeC) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}

	private boolean cmdWE_P(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : wgRegionProtect.getUtilConfig().cmdWeP) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}

	private boolean cmdWE_S(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : wgRegionProtect.getUtilConfig().cmdWeS) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}

	private boolean cmdWE_U(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : wgRegionProtect.getUtilConfig().cmdWeU) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}

	private boolean cmdWE_CP(String s) {
		s = s.replace("worldedit:", "");
		for(String tmp : wgRegionProtect.getUtilConfig().cmdWeCP) {
			if (tmp.equalsIgnoreCase(s.toLowerCase())) {
				return true;
			}
		}return false;
	}
}