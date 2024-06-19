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
                        checkDenyInteract(e, spawnVehicleType, spawnEntityType, player);
                    }
                }
            }
        }
    }

    private void checkDenyInteract(@NotNull PlayerInteractEvent e, @NotNull String spawnVehicleType, String spawnEntityType, Player player) {
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
        final String[] string = e.getMessage().toLowerCase().split(" ");
        final String cmd = e.getMessage().split(" ")[0].toLowerCase();
        if (wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
            checkIntersection(e, string, cmd);
            if (this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWeCP(string[0])) {
                e.setMessage(e.getMessage().replace("-o", ""));
                if (!this.wgrpBukkitPlugin.getCheckIntersection().checkCPIntersection(e.getPlayer(), string)) {
                    e.setCancelled(true);
                }
                wgrpBukkitPlugin.getRsApi().notify(e.getPlayer(), e.getPlayer().getName(), cmd, wgrpBukkitPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
                wgrpBukkitPlugin.getRsApi().notify(e.getPlayer().getName(), cmd, wgrpBukkitPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
            }
            if (REGION_COMMANDS_NAME.contains(string[0]) && string.length > 2) {
                for (String list : config.getRegionProtectMap().get(e.getPlayer().getLocation().getWorld().getName())) {
                    if (list.equalsIgnoreCase(string[2])) {
                        e.setCancelled(true);
                    }
                }
                for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                        e.getPlayer().getLocation()
                        .getWorld()
                        .getName())) {
                    if (list.equalsIgnoreCase(string[2])) {
                        e.setCancelled(true);
                    }
                }
                checkRegionEditArgs1(e, string);
                checkRegionEditArgs2(e, string);
                checkRegionEditArgs3(e, string);
                checkRegionEditArgs4(e, string);
            }
        }
    }

    private void checkIntersection(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string, String cmd) {
        if (this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWe(string[0]) &&
                !this.wgrpBukkitPlugin.getCheckIntersection().checkIntersection(e.getPlayer())
                || this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWeC(string[0]) &&
                !this.wgrpBukkitPlugin.getCheckIntersection().checkCIntersection(e.getPlayer(), string)
                || this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWeP(string[0]) &&
                !this.wgrpBukkitPlugin.getCheckIntersection().checkPIntersection(e.getPlayer(), string)
                || this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWeS(string[0]) &&
                !this.wgrpBukkitPlugin.getCheckIntersection().checkSIntersection(e.getPlayer(), string)
                || this.wgrpBukkitPlugin.getPlayerUtilWE().cmdWeU(string[0]) &&
                !this.wgrpBukkitPlugin.getCheckIntersection().checkUIntersection(e.getPlayer(), string)) {
            if (config.getRegionMessageProtectWe()) {
                messages.get("messages.ServerMsg.wgrpMsgWe").send(e.getPlayer());
                e.setCancelled(true);
            }
            wgrpBukkitPlugin.getRsApi().notify(
                    e.getPlayer(), e.getPlayer().getName(),
                    cmd, wgrpBukkitPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
            wgrpBukkitPlugin.getRsApi().notify(
                    e.getPlayer().getName(),
                    cmd, wgrpBukkitPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
        }
    }

    private void checkRegionEditArgs1(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        if (string.length > 3 && REGION_EDIT_ARGS.contains(string[2].toLowerCase())
                || string.length > 3 && REGION_EDIT_ARGS_FLAGS.contains(string[2].toLowerCase())) {
            for (String list : config.getRegionProtectMap().get(
                    e.getPlayer().getLocation().getWorld().getName())) {
                if (list.equalsIgnoreCase(string[3])) {
                    e.setCancelled(true);
                }
            }
            for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                    e.getPlayer().getLocation()
                    .getWorld()
                    .getName())) {
                if (list.equalsIgnoreCase(string[3])) {
                    e.setCancelled(true);
                }
            }
        }
    }

    private void checkRegionEditArgs2(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        if (string.length > 4 && string[2].equalsIgnoreCase("-w")
                || string.length > 4 && REGION_EDIT_ARGS.contains(string[2].toLowerCase())) {
            for (String list : config.getRegionProtectMap().get(
                    e.getPlayer().getLocation().getWorld().getName())) {
                if (list.equalsIgnoreCase(string[4])) {
                    e.setCancelled(true);
                }
            }
            for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                    e.getPlayer().getLocation()
                    .getWorld()
                    .getName())) {
                if (list.equalsIgnoreCase(string[4])) {
                    e.setCancelled(true);
                }
            }
        }
    }

    private void checkRegionEditArgs3(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        if (string.length > 5 && string[3].equalsIgnoreCase("-w")
                || string.length > 5 && REGION_EDIT_ARGS.contains(string[4].toLowerCase())
                || string.length > 5 && REGION_EDIT_ARGS_FLAGS.contains(string[4].toLowerCase())) {
            for (String list : config.getRegionProtectMap().get(
                    e.getPlayer().getLocation().getWorld().getName())) {
                if (list.equalsIgnoreCase(string[5])) {
                    e.setCancelled(true);
                }
            }
            for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                    e.getPlayer().getLocation()
                    .getWorld()
                    .getName())) {
                if (list.equalsIgnoreCase(string[5])) {
                    e.setCancelled(true);
                }
            }
        }
    }

    private void checkRegionEditArgs4(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        if (string.length > 6 && string[4].equalsIgnoreCase("-w")
                || string.length > 6 && string[4].equalsIgnoreCase("-h")
                || string.length > 6 && REGION_EDIT_ARGS.contains(string[5].toLowerCase())
                || string.length > 6 && REGION_EDIT_ARGS_FLAGS.contains(string[5].toLowerCase())) {
            for (String list : config.getRegionProtectMap().get(e.getPlayer().getLocation().getWorld().getName())) {
                if (list.equalsIgnoreCase(string[6])) {
                    e.setCancelled(true);
                }
            }
            for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                    e.getPlayer().getLocation()
                    .getWorld()
                    .getName())) {
                if (list.equalsIgnoreCase(string[6])) {
                    e.setCancelled(false);
                }
            }
        }
    }

}
