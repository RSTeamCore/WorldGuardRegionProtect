package net.ritasister.wgrp.listener.player;

import io.papermc.paper.event.player.PlayerFlowerPotManipulateEvent;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
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

public final class PlayerProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public PlayerProtect(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyChangeSign(@NotNull SignChangeEvent e) {
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(e.getPlayer().getLocation(), wgrpPlugin.getConfigLoader().getConfig().getRegionProtectMap())
                && wgrpPlugin.getPermissionCheck().hasPlayerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
            if (ConfigFields.SIGN_TYPE.asString(wgrpPlugin.getWgrpPaperBase()).contains(e.getBlock().getType().name().toLowerCase())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyFlowerPotManipulate(@NotNull PlayerFlowerPotManipulateEvent e) {
        final Location location = e.getFlowerpot().getLocation();
        if (ConfigFields.DENY_MANIPULATE_WITH_FLOWERPOT.asBoolean(wgrpPlugin.getWgrpPaperBase())) {
            if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigLoader().getConfig().getRegionProtectMap())
                    && wgrpPlugin.getPermissionCheck().hasPlayerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
                if (ConfigFields.NATURAL_BLOCK_OR_ITEM.asString(wgrpPlugin.getWgrpPaperBase()).contains(e.getItem().getType().name().toLowerCase())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlayerBucketEntity(@NotNull PlayerBucketEntityEvent e) {
        final Location location = e.getEntity().getLocation();
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigLoader().getConfig().getRegionProtectMap())
                && wgrpPlugin.getPermissionCheck().hasPlayerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.WATER_BUCKET) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyInteract(@NotNull PlayerInteractEvent event) {
        if (event.getItem() != null && event.getClickedBlock() != null) {
            final Player player = event.getPlayer();
            final Location location = event.getClickedBlock().getLocation();
            if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigLoader().getConfig().getRegionProtectMap())
                    && wgrpPlugin.getPermissionCheck().hasPlayerPermission(player, UtilPermissions.REGION_PROTECT)) {
                for (String spawnVehicleType : ConfigFields.VEHICLE_TYPE.asStringList(wgrpPlugin.getWgrpPaperBase())) {
                    for (String spawnEntityType : ConfigFields.INTERACT_TYPE.asStringList(wgrpPlugin.getWgrpPaperBase())) {
                        checkDenyInteract(event, spawnVehicleType, spawnEntityType, player);
                    }
                }
                if (event.getItem() != null && event.getItem().getType().toString().endsWith("_SPAWN_EGG")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    private void checkDenyInteract(@NotNull PlayerInteractEvent e, @NotNull String spawnVehicleType, String spawnEntityType, @NotNull Player player) {
        final Material mainHandItem = player.getInventory().getItemInMainHand().getType();
        final Material eventItemType = e.getItem() != null ? e.getItem().getType() : null;
        final Material clickedBlockType = e.getClickedBlock() != null ? e.getClickedBlock().getType() : null;

        if (eventItemType != null && (eventItemType == Material.getMaterial(spawnVehicleType.toUpperCase())
                || eventItemType == Material.getMaterial(spawnEntityType.toUpperCase()))) {
            e.setCancelled(true);
        } else if (mainHandItem == Material.GLOWSTONE && clickedBlockType == Material.RESPAWN_ANCHOR) {
            e.setCancelled(true);
        } else if (mainHandItem == Material.FLINT_AND_STEEL && clickedBlockType == Material.TNT) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyManipulateArmorStand(@NotNull PlayerArmorStandManipulateEvent e) {
        final Location clickLoc = e.getRightClicked().getLocation();
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(clickLoc, wgrpPlugin.getConfigLoader().getConfig().getRegionProtectMap())) {
            if (wgrpPlugin.getPermissionCheck().hasPlayerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
                if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyInteractWithItemFrame(@NotNull PlayerInteractEntityEvent e) {
        if (!ConfigFields.DENY_INTERACT_WITH_ITEM_FRAME.asBoolean(wgrpPlugin.getWgrpPaperBase())) {
            return;
        }
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(
                e.getRightClicked().getLocation(),
                wgrpPlugin.getConfigLoader().getConfig().getRegionProtectMap())) {
            if (wgrpPlugin.getPermissionCheck().hasPlayerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
                switch (e.getRightClicked().getType()) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME -> e.setCancelled(true);
                }
            }
        }
    }

}
