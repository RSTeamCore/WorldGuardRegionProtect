package net.ritasister.wgrp.listener;

import io.papermc.paper.event.player.PlayerFlowerPotManipulateEvent;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.api.config.Container;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Listens of all events interact with player.
 */
public class PlayerProtect implements Listener {

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
    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;
    private final Config config;
    private final Container messages;

    public PlayerProtect(@NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
        this.messages = wgrpBukkitPlugin.getConfigLoader().getMessages();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void checkUpdateNotifyJoinPlayer(@NotNull PlayerJoinEvent e) {
        if (!wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.ADMIN_RIGHT)) {
            wgrpBukkitPlugin.getUpdateNotify().checkUpdateNotify(
                    wgrpBukkitPlugin.getWgrpBukkitBase().getPluginMeta().getVersion(),
                    e.getPlayer(),
                    config.isUpdateChecker(),
                    config.isSendNoUpdate()
            );
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyChangeSign(@NotNull SignChangeEvent e) {
        if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(e.getPlayer().getLocation(), config.getRegionProtectMap())
                && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
            if (config.getSignType().contains(e.getBlock().getType().name().toLowerCase())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyFlowerPotManipulate(@NotNull PlayerFlowerPotManipulateEvent e) {
        final Location location = e.getFlowerpot().getLocation();
        if (config.isDenyTakeOrPlaceNaturalBlockOrItemIOFlowerPot()) {
            if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                    && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
                if (config.getNaturalBlockOrItem().contains(e.getItem().getType().name().toLowerCase())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlayerBucketEntity(@NotNull PlayerBucketEntityEvent e) {
        final Location location = e.getEntity().getLocation();
        if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.WATER_BUCKET) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyInteract(@NotNull PlayerInteractEvent e) {
        if (e.getItem() != null && e.getClickedBlock() != null) {
            final Player player = e.getPlayer();
            final Location location = e.getClickedBlock().getLocation();
            if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                    && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
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
        final Location clickLoc = e.getRightClicked().getLocation();
        if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(clickLoc, config.getRegionProtectMap())) {
            if (wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
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
        if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(
                e.getRightClicked().getLocation(),
                config.getRegionProtectMap())) {
            if (wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
                switch (e.getRightClicked().getType()) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME -> e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void denyUseWEAndWGCommand(@NotNull PlayerCommandPreprocessEvent e) {
        final String[] s = e.getMessage().toLowerCase().split(" ");
        final String cmd = e.getMessage().split(" ")[0].toLowerCase();
        if (wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
            if (this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWE(s[0]) && !this.wgrpBukkitPlugin
                    .getCheckIntersection()
                    .checkIntersection(e.getPlayer())
                    || this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWE_C(s[0]) && !this.wgrpBukkitPlugin
                    .getCheckIntersection()
                    .checkCIntersection(e.getPlayer(), s)
                    || this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWE_P(s[0]) && !this.wgrpBukkitPlugin
                    .getCheckIntersection()
                    .checkPIntersection(e.getPlayer(), s)
                    || this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWE_S(s[0]) && !this.wgrpBukkitPlugin
                    .getCheckIntersection()
                    .checkSIntersection(e.getPlayer(), s)
                    || this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWE_U(s[0]) && !this.wgrpBukkitPlugin
                    .getCheckIntersection()
                    .checkUIntersection(e.getPlayer(), s)) {
                if (config.getRegionMessageProtectWe()) {
                    messages.get("messages.ServerMsg.wgrpMsgWe").send(e.getPlayer());
                    e.setCancelled(true);
                }
                wgrpBukkitPlugin.getRsApi().notify(
                        e.getPlayer(),
                        e.getPlayer().getName(),
                        cmd,
                        wgrpBukkitPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
                wgrpBukkitPlugin.getRsApi().notify(
                        e.getPlayer().getName(),
                        cmd,
                        wgrpBukkitPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
            }
            if (this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWE_CP(s[0])) {
                e.setMessage(e.getMessage().replace("-o", ""));
                if (!this.wgrpBukkitPlugin.getCheckIntersection().checkCPIntersection(e.getPlayer(), s)) {
                    e.setCancelled(true);
                }
                wgrpBukkitPlugin.getRsApi().notify(
                        e.getPlayer(),
                        e.getPlayer().getName(),
                        cmd,
                        wgrpBukkitPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
                wgrpBukkitPlugin.getRsApi().notify(
                        e.getPlayer().getName(),
                        cmd,
                        wgrpBukkitPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer())
                );
            }
            if (REGION_COMMANDS_NAME.contains(s[0]) && s.length > 2) {
                for (String list : config.getRegionProtectMap().get(e.getPlayer().getLocation().getWorld().getName())) {
                    if (list.equalsIgnoreCase(s[2])) {
                        e.setCancelled(true);
                    }
                }
                for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                        e.getPlayer().getLocation()
                        .getWorld()
                        .getName())) {
                    if (list.equalsIgnoreCase(s[2])) {
                        e.setCancelled(true);
                    }
                }
                if (s.length > 3 && REGION_EDIT_ARGS.contains(s[2].toLowerCase())
                        || s.length > 3 && REGION_EDIT_ARGS_FLAGS.contains(s[2].toLowerCase())) {
                    for (String list : config.getRegionProtectMap().get(
                            e.getPlayer().getLocation().getWorld().getName())) {
                        if (list.equalsIgnoreCase(s[3])) {
                            e.setCancelled(true);
                        }
                    }
                    for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                            e.getPlayer().getLocation()
                            .getWorld()
                            .getName())) {
                        if (list.equalsIgnoreCase(s[3])) {
                            e.setCancelled(true);
                        }
                    }
                }
                if (s.length > 4 && s[2].equalsIgnoreCase("-w")
                        || s.length > 4 && REGION_EDIT_ARGS.contains(s[2].toLowerCase())) {
                    for (String list : config.getRegionProtectMap().get(
                            e.getPlayer().getLocation().getWorld().getName())) {
                        if (list.equalsIgnoreCase(s[4])) {
                            e.setCancelled(true);
                        }
                    }
                    for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                            e.getPlayer().getLocation()
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
                    for (String list : config.getRegionProtectMap().get(
                            e.getPlayer().getLocation().getWorld().getName())) {
                        if (list.equalsIgnoreCase(s[5])) {
                            e.setCancelled(true);
                        }
                    }
                    for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                            e.getPlayer().getLocation()
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
                    for (String list : config.getRegionProtectMap().get(e.getPlayer().getLocation().getWorld().getName())) {
                        if (list.equalsIgnoreCase(s[6])) {
                            e.setCancelled(true);
                        }
                    }
                    for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                            e.getPlayer().getLocation()
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
