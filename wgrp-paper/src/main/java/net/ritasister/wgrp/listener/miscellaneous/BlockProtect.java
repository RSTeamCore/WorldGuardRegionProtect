package net.ritasister.wgrp.listener.miscellaneous;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.manager.regions.RegionAction;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

public final class BlockProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public BlockProtect(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyBreak(@NotNull BlockBreakEvent e) {
        final Player player = e.getPlayer();
        final Location location = e.getBlock().getLocation();
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigProvider().get().getRegionProtectAllowMap())
                || wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigProvider().get().getRegionProtectOnlyBreakAllowMap())) {
            e.setCancelled(false);
        } else if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigProvider().get().getRegionProtectMap())
                && wgrpPlugin.getPermissionCheck().hasPlayerPermission(player, UtilPermissions.REGION_PROTECT)) {
            e.setCancelled(true);
            sendMessage(player);
        } else if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location)
                && wgrpPlugin.getPermissionCheck().hasPlayerPermission(player, UtilPermissions.SPY_INSPECT_ADMIN_LISTENER)) {

            spyMethod(e.getBlock(), player, location, RegionAction.Type.BREAK.getAction());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlace(@NotNull BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        final Location location = e.getBlock().getLocation();
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigProvider().get().getRegionProtectAllowMap())) {
            e.setCancelled(false);
        } else if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigProvider().get().getRegionProtectMap())
                && wgrpPlugin.getPermissionCheck().hasPlayerPermission(player, UtilPermissions.REGION_PROTECT)) {
            e.setCancelled(true);
            sendMessage(player);
        } else if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location)
                && wgrpPlugin.getPermissionCheck().hasPlayerPermission(player, UtilPermissions.SPY_INSPECT_ADMIN_LISTENER)) {

            spyMethod(e.getBlock(), player, location, RegionAction.Type.PLACE.getAction());
        }
    }

    private void spyMethod(Block block, @NotNull Player player, Location location, String regionAction) {
        if (wgrpPlugin.getSpyLog().contains(player.getPlayerProfile().getId())) {
            wgrpPlugin.getRsApi().notifyIfActionInRegion(
                    player,
                    player,
                    player.getPlayerProfile().getName(),
                    regionAction,
                    wgrpPlugin.getRegionAdapter().getProtectRegionName(location),
                    block.getX(),
                    block.getY(),
                    block.getZ(),
                    block.getWorld().getName()
            );
        }
    }

    private void sendMessage(Player player) {
        if (ConfigFields.REGION_MESSAGE_PROTECT.asBoolean(wgrpPlugin)) {
            wgrpPlugin.getMessageProvider().get().get("messages.ServerMsg.wgrpMsg").send(player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplodeRespawnAnchor(@NotNull BlockExplodeEvent e) {
        final Block block = e.getBlock();
        final Location location = block.getLocation();
        final Material blockType = block.getType();
        if (blockType == Material.RESPAWN_ANCHOR
                && wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigProvider().get().getRegionProtectMap())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void denyFormObsidianOrCobblestone(@NotNull BlockFormEvent e) {
        final Location location = e.getBlock().getLocation();
        if (!ConfigFields.DENY_FORM_BLOCK_FROM_LAVA_AND_WATER.asBoolean(wgrpPlugin)) {
            return;
        }
        if (e.getNewState().getType() == Material.OBSIDIAN || e.getNewState().getType() == Material.COBBLESTONE
                && wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigProvider().get().getRegionProtectMap())) {
            e.setCancelled(true);
        }
    }

}
