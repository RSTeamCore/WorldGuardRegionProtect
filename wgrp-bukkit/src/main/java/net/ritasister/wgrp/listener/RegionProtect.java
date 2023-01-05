package net.ritasister.wgrp.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.event.player.PlayerFlowerPotManipulateEvent;
import io.papermc.paper.event.player.PlayerLoomPatternSelectEvent;
import io.papermc.paper.event.player.PlayerStonecutterRecipeSelectEvent;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.api.RSApi;
import net.ritasister.wgrp.rslibs.api.RegionAction;
import net.ritasister.wgrp.rslibs.api.interfaces.RSRegion;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.UtilConfig;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class RegionProtect implements Listener {

    private final WorldGuardRegionProtect wgRegionProtect;

    private final RSRegion rsRegion = wgRegionProtect.getRsRegion();
    private final UtilConfig utilConfig = wgRegionProtect.getUtilConfig();
    private final Config config = wgRegionProtect.getUtilConfig().getConfig();
    private final RSApi rsApi = wgRegionProtect.getRsApi();

    static final Set<String> REGION_COMMANDS_NAME = Set.of(
            "/rg",
            "/region",
            "/regions",
            "/worldguard:rg",
            "/worldguard:region",
            "/worldguard:regions"
    );
    static final Set<String> REGION_EDIT_ARGS = Set.of("f", "flag");
    static final Set<String> REGION_EDIT_ARGS_FLAGS = Set.of("-f", "-u", "-n", "-g", "-a");

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyBreak(@NotNull BlockBreakEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        String regionName = wgRegionProtect.getRsRegion().getProtectRegionName(location);
        if (rsRegion.checkStandingRegion(location, config.getRegionProtectAllowMap())
                || rsRegion.checkStandingRegion(location, config.getRegionProtectOnlyBreakAllowMap())) {
            e.setCancelled(false);
        } else if (rsRegion.checkStandingRegion(location, config.getRegionProtectMap())
                && !rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            e.setCancelled(true);
            if (config.getRegionMessageProtect()) {
                utilConfig.getMessages().get("messages.ServerMsg.wgrpMsg").send(player);
            }
        } else if (rsRegion.checkStandingRegion(location)
                && rsApi.isPlayerListenerPermission(player, UtilPermissions.SPY_INSPECT_ADMIN_LISTENER)) {
            if (wgRegionProtect.spyLog.contains(player.getPlayerProfile().getId())) {
                rsApi.notifyIfActionInRegion(
                        player, player, player.getPlayerProfile().getName(), RegionAction.BREAK, regionName,
                        e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), e.getBlock().getWorld().getName()
                );
            }
            if (config.getDataBaseEnable()) {
                wgRegionProtect.getRsStorage().getDataSource().setLogAction(
                        player.getPlayerProfile().getName(), player.getPlayerProfile().getId(),
                        System.currentTimeMillis(), RegionAction.BREAK,
                        regionName, e.getBlock().getWorld().getName(), e.getBlock().getX(),
                        e.getBlock().getY(), e.getBlock().getZ()
                );
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlace(@NotNull BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        String regionName = wgRegionProtect.getRsRegion().getProtectRegionName(location);
        if (rsRegion.checkStandingRegion(location, config.getRegionProtectAllowMap())) {
            e.setCancelled(false);
        } else if (rsRegion.checkStandingRegion(location, config.getRegionProtectMap())
                && !rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            e.setCancelled(true);
            if (config.getRegionMessageProtect()) {
                utilConfig.getMessages().get("messages.ServerMsg.wgrpMsg").send(player);
            }
        } else if (rsRegion.checkStandingRegion(location)
                && rsApi.isPlayerListenerPermission(player, UtilPermissions.SPY_INSPECT_ADMIN_LISTENER)) {
            if (wgRegionProtect.spyLog.contains(player.getPlayerProfile().getId())) {
                rsApi.notifyIfActionInRegion(
                        player, player, player.getPlayerProfile().getName(), RegionAction.PLACE, regionName,
                        e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), e.getBlock().getWorld().getName()
                );
            }
            if (config.getDataBaseEnable()) {
                wgRegionProtect.getRsStorage().getDataSource().setLogAction(
                        player.getPlayerProfile().getName(), player.getPlayerProfile().getId(),
                        System.currentTimeMillis(), RegionAction.PLACE,
                        regionName, e.getBlock().getWorld().getName(), e.getBlock().getX(),
                        e.getBlock().getY(), e.getBlock().getZ()
                );
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void checkUpdateNotifyJoinPlayer(@NotNull PlayerJoinEvent e) {
        if (rsApi.isPlayerListenerPermission(e.getPlayer(), UtilPermissions.PERMISSION_STAR) || e.getPlayer().isOp()) {
            wgRegionProtect.checkUpdateNotify(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleCollision(@NotNull VehicleEntityCollisionEvent e) {
        if (utilConfig.getConfig().getCollisionWithVehicle()) {
            Entity entity = e.getEntity();
            Entity vehicle = e.getVehicle();
            if (entity instanceof Player) {
                if (wgRegionProtect.getRsRegion().checkStandingRegion(
                        vehicle.getLocation(),
                        this.utilConfig.getConfig().getRegionProtectMap()
                )
                        && rsApi.isEntityListenerPermission(entity, UtilPermissions.REGION_PROTECT)) {
                    if (vehicle instanceof Minecart || vehicle instanceof Boat) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleEnter(@NotNull VehicleEnterEvent e) {
        if (utilConfig.getConfig().getCanSitAsPassengerInVehicle()) {
            Entity vehicle = e.getVehicle();
            Entity entered = e.getEntered();
            if (entered instanceof Player) {
                if (wgRegionProtect.getRsRegion().checkStandingRegion(
                        vehicle.getLocation(),
                        utilConfig.getConfig().getRegionProtectMap()
                )
                        && rsApi.isEntityListenerPermission(entered, UtilPermissions.REGION_PROTECT)) {
                    if (vehicle instanceof Minecart || vehicle instanceof Boat) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleDamage(@NotNull VehicleDamageEvent e) {
        if (utilConfig.getConfig().getCanDamageVehicle()) {
            Entity vehicle = e.getVehicle();
            Entity attacker = e.getAttacker();
            if (attacker instanceof Player) {
                if (wgRegionProtect.getRsRegion().checkStandingRegion(
                        vehicle.getLocation(),
                        utilConfig.getConfig().getRegionProtectMap()
                )
                        && rsApi.isEntityListenerPermission(attacker, UtilPermissions.REGION_PROTECT)) {
                    if (vehicle instanceof Minecart || vehicle instanceof Boat) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlayerTakeLecternBook(@NotNull PlayerTakeLecternBookEvent e) {
        Player player = e.getPlayer();
        Location location = e.getLectern().getLocation();
        if (utilConfig.getConfig().isDenyTakeLecternBook()) {
            if (wgRegionProtect.getRsRegion().checkStandingRegion(location, utilConfig.getConfig().getRegionProtectMap())
                    && !rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyStonecutterRecipeSelect(@NotNull PlayerStonecutterRecipeSelectEvent e) {
        Player player = e.getPlayer();
        Location location = e.getStonecutterInventory().getLocation();
        if (utilConfig.getConfig().isDenyStonecutterRecipeSelect()) {
            if (wgRegionProtect.getRsRegion().checkStandingRegion(
                    Objects.requireNonNull(location),
                    utilConfig.getConfig().getRegionProtectMap()
            )
                    && !rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyLoomPatternSelect(@NotNull PlayerLoomPatternSelectEvent e) {
        Player player = e.getPlayer();
        Location location = e.getLoomInventory().getLocation();
        if (utilConfig.getConfig().isDenyLoomPatternSelect()) {
            if (wgRegionProtect.getRsRegion().checkStandingRegion(
                    Objects.requireNonNull(location),
                    utilConfig.getConfig().getRegionProtectMap()
            )
                    && !rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyFlowerPotManipulate(@NotNull PlayerFlowerPotManipulateEvent e) {
        Player player = e.getPlayer();
        Location location = e.getFlowerpot().getLocation();
        if (config.isDenyTakeOrPlaceNaturalBlockOrItemIOFlowerPot()) {
            if (wgRegionProtect.getRsRegion().checkStandingRegion(
                    Objects.requireNonNull(location),
                    config.getRegionProtectMap()
            )
                    && !rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                for (String naturalBlockOrItem : config.getNaturalBlockOrItem()) {
                    if (!e.isPlacing() && e.getItem().getType() == Material.getMaterial(naturalBlockOrItem.toUpperCase())) {
                        e.setCancelled(true);
                    } else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlayerBucketEntity(@NotNull PlayerBucketEntityEvent e) {
        Player player = e.getPlayer();
        Location location = e.getEntity().getLocation();
        if (wgRegionProtect.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())
                && !rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            if (player.getInventory().getItemInMainHand().getType() == Material.WATER_BUCKET) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyEntityDamageByEntityEvent(@NotNull EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity attacker = e.getDamager();
        Location location = entity.getLocation();
        if (wgRegionProtect.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())) {
            if (!(attacker instanceof Player)) {
                switch (entity.getType()) {
                    case ARMOR_STAND, ENDER_CRYSTAL, ITEM_FRAME, GLOW_ITEM_FRAME, TROPICAL_FISH, AXOLOTL,
                            TURTLE, FOX -> e.setCancelled(true);
                }
            } else if (!rsApi.isPlayerListenerPermission(
                    (Player) attacker, UtilPermissions.REGION_PROTECT)) {
                switch (entity.getType()) {
                    case ARMOR_STAND, ENDER_CRYSTAL, ITEM_FRAME, GLOW_ITEM_FRAME, TROPICAL_FISH, AXOLOTL,
                            TURTLE, FOX -> e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingBreakByEntity(@NotNull HangingBreakByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity attacker = Objects.requireNonNull(e.getRemover());
        Location location = entity.getLocation();
        if (wgRegionProtect.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())) {
            if (!(attacker instanceof Player)) {
                switch (entity.getType()) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME, PAINTING -> e.setCancelled(true);
                }
            } else if (!rsApi.isPlayerListenerPermission(
                    (Player) attacker, UtilPermissions.REGION_PROTECT)) {
                switch (entity.getType()) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME, PAINTING -> e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void denyBlockFromTo(@NotNull BlockFromToEvent e) {
        Block block = e.getBlock();
        Location location = e.getToBlock().getLocation();
        if (wgRegionProtect.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())) {
            if (config.isDenyWaterFlowToRegion() && block.getType() == Material.WATER
                    || config.isDenyLavaFlowToRegion() && block.getType() == Material.LAVA) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyInteract(@NotNull PlayerInteractEvent e) {
        if (e.getItem() != null && e.getClickedBlock() != null) {
            Player player = e.getPlayer();
            Location location = e.getClickedBlock().getLocation();
            if (wgRegionProtect.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())
                    && !rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                for (String spawnEntityType : config.getInteractType()) {
                    if (e.getItem().getType() == Material.getMaterial(spawnEntityType.toUpperCase())
                            && e.getClickedBlock() != null) {
                        e.setCancelled(true);
                    } else if (player.getInventory().getItemInMainHand().getType() == Material.GLOWSTONE
                            && e.getClickedBlock() != null
                            && e.getClickedBlock().getType() == Material.RESPAWN_ANCHOR) {
                        e.setCancelled(true);
                    } else if (player.getInventory().getItemInMainHand().getType() == Material.FLINT_AND_STEEL
                            && e.getClickedBlock() != null
                            && e.getClickedBlock().getType() == Material.TNT) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyInteractWithEntity(@NotNull PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        Location clickLoc = e.getRightClicked().getLocation();
        EntityType clickType = e.getRightClicked().getType();
        if (wgRegionProtect.getRsRegion().checkStandingRegion(clickLoc, config.getRegionProtectMap())) {
            if (!rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                switch (clickType) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME -> e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyManipulateArmorStand(@NotNull PlayerArmorStandManipulateEvent e) {
        Player player = e.getPlayer();
        Location clickLoc = e.getRightClicked().getLocation();
        if (wgRegionProtect.getRsRegion().checkStandingRegion(clickLoc, config.getRegionProtectMap())) {
            if (!rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyStructureGrow(@NotNull StructureGrowEvent e) {
        if (e.getPlayer() != null) {
            Player player = e.getPlayer();
            if (wgRegionProtect.getRsRegion().checkStandingRegion(
                    e.getLocation(),
                    config.getRegionProtectMap()
            )) {
                if (!rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingPlace(@NotNull HangingPlaceEvent e) {
        Entity entity = e.getEntity();
        Player player = e.getPlayer();
        Location loc = entity.getLocation();
        if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, config.getRegionProtectMap())) {
            if (!rsApi.isPlayerListenerPermission(Objects.requireNonNull(player), UtilPermissions.REGION_PROTECT)) {
                switch (player.getInventory().getItemInMainHand().getType()) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME, PAINTING -> e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplode(@NotNull EntityExplodeEvent e) {
        Entity entity = e.getEntity();
        EntityType entityType = e.getEntityType();
        Location loc = entity.getLocation();
        if (wgRegionProtect.getRsRegion().checkStandingRegion(loc, config.getRegionProtectMap())) {
            if (config.getExplodeEntity()) {
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
    private void denyExplodeRespawnAnchor(@NotNull BlockExplodeEvent e) {
        Block block = e.getBlock();
        Location location = block.getLocation();
        Material blockType = block.getType();
        if (blockType == Material.RESPAWN_ANCHOR
                && wgRegionProtect.getRsRegion().checkStandingRegion(
                location, config.getRegionProtectMap())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void denyUseWEAndIwgCommand(@NotNull PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();
        Location location = player.getLocation();
        String[] s = e.getMessage().toLowerCase().split(" ");
        String cmd = e.getMessage().split(" ")[0].toLowerCase();
        if (!rsApi.isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            if (this.wgRegionProtect.getCommandWE().cmdWE(s[0]) && !this.wgRegionProtect.getWg().checkIntersection(player)
                    || this.wgRegionProtect.getCommandWE().cmdWE_C(s[0]) && !this.wgRegionProtect.getWg().checkCIntersection(
                    player,
                    s
            )
                    || this.wgRegionProtect.getCommandWE().cmdWE_P(s[0]) && !this.wgRegionProtect.getWg().checkPIntersection(
                    player,
                    s
            )
                    || this.wgRegionProtect.getCommandWE().cmdWE_S(s[0]) && !this.wgRegionProtect.getWg().checkSIntersection(
                    player,
                    s
            )
                    || this.wgRegionProtect.getCommandWE().cmdWE_U(s[0]) && !this.wgRegionProtect.getWg().checkUIntersection(
                    player,
                    s
            )) {
                if (config.getRegionMessageProtectWe()) {
                    utilConfig.getMessages().get("messages.ServerMsg.wgrpMsgWe").send(player);
                    e.setCancelled(true);
                }
                rsApi.notify(player, playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(player));
                rsApi.notify(playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(player));
            }
            if (this.wgRegionProtect.getCommandWE().cmdWE_CP(s[0])) {
                e.setMessage(e.getMessage().replace("-o", ""));
                if (!this.wgRegionProtect.getWg().checkCPIntersection(player, s)) {
                    e.setCancelled(true);
                }
                rsApi.notify(player, playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(player));
                rsApi.notify(playerName, cmd, wgRegionProtect.getRsRegion().getProtectRegionName(player));
            }
            if (REGION_COMMANDS_NAME.contains(s[0]) && s.length > 2) {
                for (String list : config.getRegionProtectMap().get(location.getWorld().getName())) {
                    if (list.equalsIgnoreCase(s[2])) {
                        e.setCancelled(true);
                    }
                }
                for (String list : config.getRegionProtectOnlyBreakAllowMap().get(location
                        .getWorld()
                        .getName())) {
                    if (list.equalsIgnoreCase(s[2])) {
                        e.setCancelled(true);
                    }
                }
                if (s.length > 3 && REGION_EDIT_ARGS.contains(s[2].toLowerCase())
                        || s.length > 3 && REGION_EDIT_ARGS_FLAGS.contains(s[2].toLowerCase())) {
                    for (String list : config.getRegionProtectMap().get(location.getWorld().getName())) {
                        if (list.equalsIgnoreCase(s[3])) {
                            e.setCancelled(true);
                        }
                    }
                    for (String list : config.getRegionProtectOnlyBreakAllowMap().get(location
                            .getWorld()
                            .getName())) {
                        if (list.equalsIgnoreCase(s[3])) {
                            e.setCancelled(true);
                        }
                    }
                }
                if (s.length > 4 && s[2].equalsIgnoreCase("-w")
                        || s.length > 4 && REGION_EDIT_ARGS.contains(s[2].toLowerCase())) {
                    for (String list : config.getRegionProtectMap().get(location.getWorld().getName())) {
                        if (list.equalsIgnoreCase(s[4])) {
                            e.setCancelled(true);
                        }
                    }
                    for (String list : config.getRegionProtectOnlyBreakAllowMap().get(location
                            .getWorld()
                            .getName())) {
                        if (list.equalsIgnoreCase(s[4])) {
                            e.setCancelled(true);
                        }
                    }
                }
                if (s.length > 5 && s[3].equalsIgnoreCase("-w")
                        || s.length > 5 && REGION_EDIT_ARGS.contains(s[4].toLowerCase())
                        || s.length > 5 && REGION_EDIT_ARGS_FLAGS.contains(s[4].toLowerCase())) {
                    for (String list : config.getRegionProtectMap().get(location.getWorld().getName())) {
                        if (list.equalsIgnoreCase(s[5])) {
                            e.setCancelled(true);
                        }
                    }
                    for (String list : config.getRegionProtectOnlyBreakAllowMap().get(location
                            .getWorld()
                            .getName())) {
                        if (list.equalsIgnoreCase(s[5])) {
                            e.setCancelled(true);
                        }
                    }
                }
                if (s.length > 6 && s[4].equalsIgnoreCase("-w")
                        || s.length > 6 && s[4].equalsIgnoreCase("-h")
                        || s.length > 6 && REGION_EDIT_ARGS.contains(s[5].toLowerCase())
                        || s.length > 6 && REGION_EDIT_ARGS_FLAGS.contains(s[5].toLowerCase())) {
                    for (String list : config.getRegionProtectMap().get(location.getWorld().getName())) {
                        if (list.equalsIgnoreCase(s[6])) {
                            e.setCancelled(true);
                        }
                    }
                    for (String list : config.getRegionProtectOnlyBreakAllowMap().get(location
                            .getWorld()
                            .getName())) {
                        if (list.equalsIgnoreCase(s[6])) {
                            e.setCancelled(false);
                        }
                    }
                }
            }
        }
    }

}
