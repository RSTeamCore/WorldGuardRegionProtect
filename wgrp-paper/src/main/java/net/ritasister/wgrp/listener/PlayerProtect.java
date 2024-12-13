package net.ritasister.wgrp.listener;

import io.papermc.paper.event.player.PlayerFlowerPotManipulateEvent;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.file.config.Config;
import net.ritasister.wgrp.util.file.config.ConfigFields;
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
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;
    private final Config config;

    public PlayerProtect(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
        this.config = wgrpPlugin.getConfigLoader().getConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyChangeSign(@NotNull SignChangeEvent e) {
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(e.getPlayer().getLocation(), config.getRegionProtectMap())
                && wgrpPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
            if (ConfigFields.SIGN_TYPE.get(wgrpPlugin.getWgrpPaperBase()).toString().contains(e.getBlock().getType().name().toLowerCase())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyFlowerPotManipulate(@NotNull PlayerFlowerPotManipulateEvent e) {
        final Location location = e.getFlowerpot().getLocation();
        if (ConfigFields.DENY_MANIPULATE_WITH_FLOWERPOT.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
            if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                    && wgrpPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
                if (ConfigFields.NATURAL_BLOCK_OR_ITEM.get(wgrpPlugin.getWgrpPaperBase()).toString().contains(e.getItem().getType().name().toLowerCase())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlayerBucketEntity(@NotNull PlayerBucketEntityEvent e) {
        final Location location = e.getEntity().getLocation();
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                && wgrpPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
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
            if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                    && wgrpPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                for (String spawnVehicleType : ConfigFields.VEHICLE_TYPE.getList(wgrpPlugin.getWgrpPaperBase())) {
                    for (String spawnEntityType : ConfigFields.INTERACT_TYPE.getList(wgrpPlugin.getWgrpPaperBase())) {
                        checkDenyInteract(e, spawnVehicleType, spawnEntityType, player);
                    }
                }
            }
        }
    }

    private void checkDenyInteract(@NotNull PlayerInteractEvent e, @NotNull String spawnVehicleType, String spawnEntityType, Player player) {
        if (e.getItem() != null && e.getItem().getType() == Material.getMaterial(spawnVehicleType.toUpperCase())
                || e.getItem() != null && e.getItem().getType() == Material.getMaterial(spawnEntityType.toUpperCase())
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
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(clickLoc, config.getRegionProtectMap())) {
            if (wgrpPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
                if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyInteractWithItemFrame(@NotNull PlayerInteractEntityEvent e) {
        if (!ConfigFields.DENY_INTERACT_WITH_ITEM_FRAME.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
            return;
        }
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(
                e.getRightClicked().getLocation(),
                config.getRegionProtectMap())) {
            if (wgrpPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
                switch (e.getRightClicked().getType()) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME -> e.setCancelled(true);
                }
            }
        }
    }

}
