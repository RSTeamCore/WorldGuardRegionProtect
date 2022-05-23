package net.ritasister.wgrp.listener.protect;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.api.RegionAction;
import net.ritasister.wgrp.rslibs.permissions.IUtilPermissions;
import net.ritasister.wgrp.util.config.Message;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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

	public RegionProtect(WorldGuardRegionProtect worldGuardRegionProtect) {
		this.wgRegionProtect =worldGuardRegionProtect;
	}
	private final List<String> regionCommandNameArgs = Arrays.asList(
			"/rg", "/region", "/regions",
			"/worldguard:rg", "/worldguard:region", "/worldguard:regions");
	private final List<String> regionEditArgs = Arrays.asList("f", "flag");
	private final List<String> regionEditArgsFlags = Arrays.asList("-f", "-u", "-n", "-g", "-a");

	@EventHandler(priority = EventPriority.LOW)
	private void denyBreak(final @NotNull BlockBreakEvent e) {
		final Player player = e.getPlayer();
		final String playerName = player.getPlayerProfile().getName();
		final UUID uniqueId = player.getPlayerProfile().getId();
		final Block block = e.getBlock();
		final Location loc = block.getLocation();
		String regionName = wgRegionProtect.getRsRegion().getProtectRegion(loc);
		if(wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtectAllow())
				|| wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtectOnlyBreakAllow())) {
			e.setCancelled(false);
		} else if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())
				&& !wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) {
			e.setCancelled(true);
			if (wgRegionProtect.getUtilConfig().getConfig().getRegionMessageProtect()) {
				player.sendMessage(Message.wgrpMsg.toString());
			}
		} else if (wgRegionProtect.getRsRegion().checkStandingRegion(loc)
				&& wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.SPY_INSPECT_ADMIN_LISTENER, null)) {
			if(wgRegionProtect.spyLog.contains(uniqueId)) {
				wgRegionProtect.getRsApi().notifyIfActionInRegion(
						player, player, wgRegionProtect.getRsApi().getTime(),
						playerName, RegionAction.BREAK, regionName,
						block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
			}
			if(wgRegionProtect.getUtilConfig().getConfig().getDataBaseEnable()) {
				wgRegionProtect.getRsStorage().getDataSource().setLogAction(
						playerName, uniqueId,
						System.currentTimeMillis(), RegionAction.BREAK,
						regionName, block.getWorld().getName(), block.getX(),
						block.getY(), block.getZ());
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	private void denyPlace(final @NotNull BlockPlaceEvent e) {
		final Player player = e.getPlayer();
		final String playerName = player.getPlayerProfile().getName();
		final UUID uniqueId = player.getPlayerProfile().getId();
		final Block block = e.getBlock();
		final Location loc = block.getLocation();
		String regionName = wgRegionProtect.getRsRegion().getProtectRegion(loc);
		if(wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtectAllow())) {
			e.setCancelled(false);
		} else if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())
				&& !wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null)) {
			e.setCancelled(true);
			if (wgRegionProtect.getUtilConfig().getConfig().getRegionMessageProtect()) {
				player.sendMessage(Message.wgrpMsg.toString());
			}
		} else if (wgRegionProtect.getRsRegion().checkStandingRegion(loc)
				&& wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.SPY_INSPECT_ADMIN_LISTENER, null)) {
			if(wgRegionProtect.spyLog.contains(uniqueId)) {
				wgRegionProtect.getRsApi().notifyIfActionInRegion(
						player, player, wgRegionProtect.getRsApi().getTime(),
						playerName, RegionAction.PLACE, regionName,
						block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
			}
			if(wgRegionProtect.getUtilConfig().getConfig().getRegionMessageProtect()) {
				wgRegionProtect.getRsStorage().getDataSource().setLogAction(
						playerName, uniqueId,
						System.currentTimeMillis(), RegionAction.PLACE,
						regionName, block.getWorld().getName(), block.getX(),
						block.getY(), block.getZ());
			}
		}
	}
	/*@EventHandler(priority = EventPriority.LOW)
	private void denyVehicleCollision(@NotNull VehicleEntityCollisionEvent e) {
		Entity entity = e.getEntity();
		Entity vehicle = e.getVehicle();
		Location vehicleLocation = e.getVehicle().getLocation();
		if (wgRegionProtect.getRsRegion().checkStandingRegion(vehicleLocation)) {
			if(entity instanceof ItemFrame) {
				if(vehicle instanceof Boat) {
					e.setCancelled(true);
				}
			}
		}
	}*/

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyPlayerTakeLecternBook(final @NotNull PlayerTakeLecternBookEvent e) {
		Player player = e.getPlayer();
		Location location = e.getLectern().getLocation();
		if (wgRegionProtect.getRsRegion().checkStandingRegion(location)) {
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyBucketEmpty(final @NotNull PlayerBucketEmptyEvent e) {
		final Player p = e.getPlayer();
		final Location loc = e.getBlockClicked().getLocation();
		final Material bucketType = e.getBucket();
		if(wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(p, IUtilPermissions.REGION_PROTECT, null))return;{
				switch (bucketType) {
					case LAVA_BUCKET, WATER_BUCKET, TROPICAL_FISH_BUCKET, PUFFERFISH_BUCKET,
							AXOLOTL_BUCKET, COD_BUCKET, SALMON_BUCKET -> e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyPlayerBucketEntity(final @NotNull PlayerBucketEntityEvent e) {
		final Player player = e.getPlayer();
		final Location loc = e.getEntity().getLocation();
		final Material bucketInHand = player.getInventory().getItemInMainHand().getType();
		if(wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
				if (bucketInHand == Material.WATER_BUCKET) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyEntityDamageByEntityEvent(final @NotNull EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		Entity attacker = e.getDamager();
		final Location loc = entity.getLocation();
		if(wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(attacker, IUtilPermissions.REGION_PROTECT, null))return;{
				switch(entity.getType()) {
					case ARMOR_STAND, ITEM_FRAME, GLOW_ITEM_FRAME, TROPICAL_FISH, AXOLOTL,
							TURTLE, FOX -> e.setCancelled(true);
					default -> {
						switch(attacker.getType()) {
							case PLAYER, CREEPER, PRIMED_TNT, ENDER_CRYSTAL, WITHER,
									WITHER_SKELETON, GHAST, SNOWMAN, SHULKER_BULLET,
									DROWNED, SKELETON, STRAY -> e.setCancelled(true);
							default -> {
								if(attacker instanceof Projectile) {
									attacker = ((Projectile) attacker).getShooter() instanceof Entity
											? (Entity) ((Projectile) attacker).getShooter() : null;
									switch (Objects.requireNonNull(attacker).getType()) {
										case PLAYER, CREEPER, PRIMED_TNT, ENDER_CRYSTAL, WITHER,
												WITHER_SKELETON, GHAST, SNOWMAN, SHULKER_BULLET,
												DROWNED, SKELETON, STRAY -> e.setCancelled(true);
									}
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
		if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
			assert attacker != null;
			if (wgRegionProtect.getRsApi().isSenderListenerPermission(attacker, IUtilPermissions.REGION_PROTECT, null)) return; {
				switch(entity.getType()) {
					case ITEM_FRAME, GLOW_ITEM_FRAME, PAINTING -> e.setCancelled(true);
						default -> {
							switch(attacker.getType()) {
								case PLAYER, CREEPER, PRIMED_TNT, ENDER_CRYSTAL, WITHER,
										WITHER_SKELETON, GHAST, SNOWMAN, SHULKER_BULLET,
										DROWNED, SKELETON, STRAY -> e.setCancelled(true);
								default -> {
									if(attacker instanceof Projectile) {
										attacker = ((Projectile) attacker).getShooter() instanceof Entity
												? (Entity) ((Projectile) attacker).getShooter() : null;
										switch (Objects.requireNonNull(attacker).getType()) {
											case PLAYER, CREEPER, PRIMED_TNT, ENDER_CRYSTAL, WITHER,
													WITHER_SKELETON, GHAST, SNOWMAN, SHULKER_BULLET,
													DROWNED, SKELETON, STRAY -> e.setCancelled(true);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyInteract(final @NotNull PlayerInteractEvent e) {
		if (e.getItem() != null) {
			Player player = e.getPlayer();
			if(wgRegionProtect.getRsRegion().checkStandingRegion(player.getWorld(), player.getLocation(), wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
				if(wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
					for (String spawnEntityType : wgRegionProtect.getUtilConfig().getConfig().getInteractType()) {
						if (e.getItem().getType() == Material.getMaterial(spawnEntityType.toUpperCase())
								&& e.getClickedBlock() != null) {
							e.setCancelled(true);
						} else if (player.getInventory().getItemInMainHand().getType() == Material.GLOWSTONE
								&& e.getClickedBlock() != null
								&& e.getClickedBlock().getType() == Material.RESPAWN_ANCHOR) {
							e.setCancelled(true);
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
		final @NotNull EntityType clickType = e.getRightClicked().getType();
		if(wgRegionProtect.getRsRegion().checkStandingRegion(clickLoc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(player, IUtilPermissions.REGION_PROTECT, null))return;{
				if(clickType == EntityType.ITEM_FRAME || clickType == EntityType.GLOW_ITEM_FRAME) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyManipulateArmorStand(final @NotNull PlayerArmorStandManipulateEvent e) {
		final Player p = e.getPlayer();
		final Location clickLoc = e.getRightClicked().getLocation();
		if(wgRegionProtect.getRsRegion().checkStandingRegion(clickLoc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
			if(wgRegionProtect.getRsApi().isSenderListenerPermission(p, IUtilPermissions.REGION_PROTECT, null))return;{
				if(e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyStructureGrow(final @NotNull StructureGrowEvent e) {
		if (this.wgRegionProtect.getRsRegion().checkStandingRegion(e.getWorld(), e.getLocation(), wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyHangingPlace(final @NotNull HangingPlaceEvent e) {
		final Entity entity = e.getEntity();
		@NotNull final Player player = Objects.requireNonNull(e.getPlayer());
		final Location loc = entity.getLocation();
		if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
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
		if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
			if(wgRegionProtect.getUtilConfig().getConfig().getExplodeEntity()) {
				switch (entityType) {
					case PRIMED_TNT, ENDER_CRYSTAL, MINECART_TNT, CREEPER, FIREBALL, WITHER_SKULL -> e.blockList().clear();
					default -> e.setCancelled(true);
				}
			} else {
				switch (entityType) {
					case PRIMED_TNT, ENDER_CRYSTAL, MINECART_TNT, CREEPER, FIREBALL, WITHER_SKULL -> e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void denyExplodeRespawnAnchor(final @NotNull BlockExplodeEvent e) {
		final Block block = e.getBlock();
		final Location loc = block.getLocation();
		final @NotNull Material blockType = block.getType();
		if (blockType == Material.RESPAWN_ANCHOR && wgRegionProtect.getRsRegion().checkStandingRegion(loc, wgRegionProtect.getUtilConfig().getConfig().getRegionProtect())) {
			e.setCancelled(true);
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
			if (this.wgRegionProtect.getCommandWE().cmdWE(s[0]) && !this.wgRegionProtect.getIwg().checkIntersection(player)) {
				e.setCancelled(true);
				if (wgRegionProtect.getUtilConfig().getConfig().getRegionMessageProtectWe()) {
					player.sendMessage(Message.wgrpMsgWe.toString());
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
			}if (this.wgRegionProtect.getCommandWE().cmdWE_C(s[0]) && !this.wgRegionProtect.getIwg().checkCIntersection(player, s)) {
				e.setCancelled(true);
				if (wgRegionProtect.getUtilConfig().getConfig().getRegionMessageProtectWe()) {
					player.sendMessage(Message.wgrpMsgWe.toString());
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
			}if (this.wgRegionProtect.getCommandWE().cmdWE_P(s[0]) && !this.wgRegionProtect.getIwg().checkPIntersection(player, s)) {
				e.setCancelled(true);
				if (wgRegionProtect.getUtilConfig().getConfig().getRegionMessageProtectWe()) {
					player.sendMessage(Message.wgrpMsgWe.toString());
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
			}if (this.wgRegionProtect.getCommandWE().cmdWE_S(s[0]) && !this.wgRegionProtect.getIwg().checkSIntersection(player, s)) {
				e.setCancelled(true);
				if (wgRegionProtect.getUtilConfig().getConfig().getRegionMessageProtectWe()) {
					player.sendMessage(Message.wgrpMsgWe.toString());
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
			}if (this.wgRegionProtect.getCommandWE().cmdWE_U(s[0]) && !this.wgRegionProtect.getIwg().checkUIntersection(player, s)) {
				e.setCancelled(true);
				if (wgRegionProtect.getUtilConfig().getConfig().getRegionMessageProtectWe()) {
					player.sendMessage(Message.wgrpMsgWe.toString());
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
			}if (this.wgRegionProtect.getCommandWE().cmdWE_CP(s[0])) {
				e.setMessage(e.getMessage().replace("-o", ""));
				if (!this.wgRegionProtect.getIwg().checkCPIntersection(player, s)) {
					e.setCancelled(true);
				}
				wgRegionProtect.getRsApi().notify(player, playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
				wgRegionProtect.getRsApi().notify(playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(loc));
			}
			if (this.regionCommandNameArgs.contains(s[0]) && s.length > 2) {
				for (final String list : wgRegionProtect.getUtilConfig().getConfig().getRegionProtect()) {
					if (list.equalsIgnoreCase(s[2])) {
						e.setCancelled(true);
					}
				}for (final String list : wgRegionProtect.getUtilConfig().getConfig().getRegionProtectOnlyBreakAllow()) {
					if (list.equalsIgnoreCase(s[2])) {
						e.setCancelled(true);
					}
				}
				if (s.length > 3 && this.regionEditArgs.contains(s[2].toLowerCase())
						|| s.length > 3 && this.regionEditArgsFlags.contains(s[2].toLowerCase())) {
					for (final String list : wgRegionProtect.getUtilConfig().getConfig().getRegionProtect()) {
						if (list.equalsIgnoreCase(s[3])) {
							e.setCancelled(true);
						}
					}for (final String list : wgRegionProtect.getUtilConfig().getConfig().getRegionProtectOnlyBreakAllow()) {
						if (list.equalsIgnoreCase(s[3])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 4 && s[2].equalsIgnoreCase("-w")
						|| s.length > 4 && this.regionEditArgsFlags.contains(s[2].toLowerCase())) {
					for (final String list : wgRegionProtect.getUtilConfig().getConfig().getRegionProtect()) {
						if (list.equalsIgnoreCase(s[4])) {
							e.setCancelled(true);
						}
					}for (final String list : wgRegionProtect.getUtilConfig().getConfig().getRegionProtectOnlyBreakAllow()) {
						if (list.equalsIgnoreCase(s[4])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 5 && s[3].equalsIgnoreCase("-w")
						|| s.length > 5 && this.regionEditArgs.contains(s[4].toLowerCase())
						|| s.length > 5 && this.regionEditArgsFlags.contains(s[4].toLowerCase())) {
					for (final String list : wgRegionProtect.getUtilConfig().getConfig().getRegionProtect()) {
						if (list.equalsIgnoreCase(s[5])) {
							e.setCancelled(true);
						}
					}for (final String list : wgRegionProtect.getUtilConfig().getConfig().getRegionProtectOnlyBreakAllow()) {
						if (list.equalsIgnoreCase(s[5])) {
							e.setCancelled(true);
						}
					}
				}
				if (s.length > 6 && s[4].equalsIgnoreCase("-w")
						|| s.length > 6 && s[4].equalsIgnoreCase("-h")
						|| s.length > 6 && this.regionEditArgs.contains(s[5].toLowerCase())
						|| s.length > 6 && this.regionEditArgsFlags.contains(s[5].toLowerCase())) {
					for (final String list : wgRegionProtect.getUtilConfig().getConfig().getRegionProtect()) {
						if (list.equalsIgnoreCase(s[6])) {
							e.setCancelled(true);
						}
					}
					for (final String list : wgRegionProtect.getUtilConfig().getConfig().getRegionProtectOnlyBreakAllow()) {
						if (list.equalsIgnoreCase(s[6])) {
							e.setCancelled(false);
						}
					}
				}
			}
		}
	}
}