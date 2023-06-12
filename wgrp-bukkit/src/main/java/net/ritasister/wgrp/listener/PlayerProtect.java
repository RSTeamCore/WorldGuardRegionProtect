package net.ritasister.wgrp.listener;

import io.papermc.paper.event.player.PlayerFlowerPotManipulateEvent;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class PlayerProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    private final Config config;

    public PlayerProtect(@NotNull WGRPContainer wgrpContainer) {
        this.wgrpContainer = wgrpContainer;
        this.config = wgrpContainer.getConfig();
    }


    static final Set<String> REGION_COMMANDS_NAME = Set.of(
            "/rg",
            "/region",
            "/regions",
            "/worldguard:rg",
            "/worldguard:region",
            "/worldguard:regions");
    static final Set<String> REGION_EDIT_ARGS = Set.of("f", "flag");
    static final Set<String> REGION_EDIT_ARGS_FLAGS = Set.of("-f", "-u", "-n", "-g", "-a");


    @EventHandler(priority = EventPriority.NORMAL)
    private void checkUpdateNotifyJoinPlayer(@NotNull PlayerJoinEvent e) {
        if (wgrpContainer.getRsApi().isPlayerListenerPermission(
                e.getPlayer(), UtilPermissions.PERMISSION_STAR) || e.getPlayer().isOp()) {
            wgrpContainer.getUpdateNotify().checkUpdateNotify(wgrpContainer.getPluginMeta(), e.getPlayer(), config.getUpdateChecker()
            );
        }
    }

    //TODO
    @EventHandler(priority = EventPriority.LOWEST)
    private void denyFlowerPotManipulate(@NotNull PlayerFlowerPotManipulateEvent e) {
        Player player = e.getPlayer();
        Location location = e.getFlowerpot().getLocation();
        if (config.isDenyTakeOrPlaceNaturalBlockOrItemIOFlowerPot()) {
            if (wgrpContainer.getRsRegion().checkStandingRegion(Objects.requireNonNull(location), config.getRegionProtectMap())
                    && !wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {

                for (String naturalBlockOrItem : config.getNaturalBlockOrItem()) {
                    if (e.getItem().getType() == Material.getMaterial(naturalBlockOrItem.toUpperCase())) {
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
        if (wgrpContainer.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())
                && !wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            if (player.getInventory().getItemInMainHand().getType() == Material.WATER_BUCKET) {
                e.setCancelled(true);
            }
        }
    }

    //TODO
    @EventHandler(priority = EventPriority.LOWEST)
    private void denyInteract(@NotNull PlayerInteractEvent e) {
        if (e.getItem() != null && e.getClickedBlock() != null) {
            Player player = e.getPlayer();
            Location location = e.getClickedBlock().getLocation();
            if (wgrpContainer.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())
                    && !wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                for (String spawnVehicleType : config.getVehicleType()) {
                    for (String spawnEntityType : config.getInteractType()) {
                        if (e.getItem().getType() == Material.getMaterial(spawnVehicleType.toUpperCase())
                                || e.getItem().getType() == Material.getMaterial(spawnEntityType.toUpperCase())
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
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyManipulateArmorStand(@NotNull PlayerArmorStandManipulateEvent e) {
        Player player = e.getPlayer();
        Location clickLoc = e.getRightClicked().getLocation();
        if (wgrpContainer.getRsRegion().checkStandingRegion(clickLoc, config.getRegionProtectMap())) {
            if (!wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyInteractWithEntity(@NotNull PlayerInteractEntityEvent e) {
        if (!config.isDenyInteractWithItemFrame()) {
            return;
        }
        Player player = e.getPlayer();
        Location clickLoc = e.getRightClicked().getLocation();
        EntityType clickType = e.getRightClicked().getType();
        if (wgrpContainer.getRsRegion().checkStandingRegion(clickLoc, config.getRegionProtectMap())) {
            if (!wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                switch (clickType) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME -> e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void denyUseWEAndWGCommand(@NotNull PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();
        Location location = player.getLocation();
        String[] s = e.getMessage().toLowerCase().split(" ");
        String cmd = e.getMessage().split(" ")[0].toLowerCase();
        if (!wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            if (this.wgrpContainer.getCommandWE().cmdWE(s[0]) && !this.wgrpContainer.getWg().checkIntersection(player)
                    || this.wgrpContainer.getCommandWE().cmdWE_C(s[0]) && !this.wgrpContainer.getWg().checkCIntersection(player, s)
                    || this.wgrpContainer.getCommandWE().cmdWE_P(s[0]) && !this.wgrpContainer.getWg().checkPIntersection(player, s)
                    || this.wgrpContainer.getCommandWE().cmdWE_S(s[0]) && !this.wgrpContainer.getWg().checkSIntersection(player, s)
                    || this.wgrpContainer.getCommandWE().cmdWE_U(s[0]) && !this.wgrpContainer.getWg().checkUIntersection(player, s)) {
                if (config.getRegionMessageProtectWe()) {
                    wgrpContainer.getMessages().get("messages.ServerMsg.wgrpMsgWe").send(player);
                    e.setCancelled(true);
                }
                wgrpContainer.getRsApi().notify(
                        player,
                        playerName,
                        cmd,
                        wgrpContainer.getRsRegion().getProtectRegionNameBySelection(player)
                );
                wgrpContainer.getRsApi().notify(
                        playerName,
                        cmd,
                        wgrpContainer.getRsRegion().getProtectRegionNameBySelection(player)
                );
            }
            if (this.wgrpContainer.getCommandWE().cmdWE_CP(s[0])) {
                e.setMessage(e.getMessage().replace("-o", ""));
                if (!this.wgrpContainer.getWg().checkCPIntersection(player, s)) {
                    e.setCancelled(true);
                }
                wgrpContainer.getRsApi().notify(
                        player,
                        playerName,
                        cmd,
                        wgrpContainer.getRsRegion().getProtectRegionNameBySelection(player)
                );
                wgrpContainer.getRsApi().notify(
                        playerName,
                        cmd,
                        wgrpContainer.getRsRegion().getProtectRegionNameBySelection(player)
                );
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
