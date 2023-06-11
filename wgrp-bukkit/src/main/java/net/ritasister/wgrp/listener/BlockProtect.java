package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.api.RegionAction;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

public class BlockProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    private final Config config;

    public BlockProtect(@NotNull WGRPContainer wgrpContainer) {
        this.wgrpContainer = wgrpContainer;
        this.config = wgrpContainer.getConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyBreak(@NotNull BlockBreakEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        if (wgrpContainer.getRsRegion().checkStandingRegion(location, config.getRegionProtectAllowMap())
                || wgrpContainer.getRsRegion().checkStandingRegion(location, config.getRegionProtectOnlyBreakAllowMap())) {
            e.setCancelled(false);
        } else if (wgrpContainer.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())
                && !wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            e.setCancelled(true);
            sendMessage(player);
        } else if (wgrpContainer.getRsRegion().checkStandingRegion(location)
                && wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.SPY_INSPECT_ADMIN_LISTENER)) {

            spyMethod(e.getBlock(), player, location, RegionAction.BREAK);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlace(@NotNull BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        if (wgrpContainer.getRsRegion().checkStandingRegion(location, config.getRegionProtectAllowMap())) {
            e.setCancelled(false);
        } else if (wgrpContainer.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())
                && !wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            e.setCancelled(true);

            sendMessage(player);
        } else if (wgrpContainer.getRsRegion().checkStandingRegion(location)
                && wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.SPY_INSPECT_ADMIN_LISTENER)) {

            spyMethod(e.getBlock(), player, location, RegionAction.PLACE);
        }
    }

    private void spyMethod(Block block, @NotNull Player player, Location location, RegionAction regionAction) {
        if (wgrpContainer.getSpyLog().contains(player.getPlayerProfile().getId())) {
            wgrpContainer.getRsApi().notifyIfActionInRegion(
                    player,
                    player,
                    player.getPlayerProfile().getName(),
                    regionAction,
                    wgrpContainer.getRsRegion().getProtectRegionName(location),
                    block.getX(),
                    block.getY(),
                    block.getZ(),
                    block.getWorld().getName()
            );
        }
        if (config.getDataBaseEnable()) {
            wgrpContainer.getRsStorage().getDataSource().setLogAction(
                    player.getPlayerProfile().getName(), player.getPlayerProfile().getId(),
                    System.currentTimeMillis(), regionAction,
                    wgrpContainer.getRsRegion().getProtectRegionName(location), block.getWorld().getName(), block.getX(),
                    block.getY(), block.getZ());
        }
    }

    private void sendMessage(Player player) {
        if (config.getRegionMessageProtect()) {
            wgrpContainer.getMessages().get("messages.ServerMsg.wgrpMsg").send(player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplodeRespawnAnchor(@NotNull BlockExplodeEvent e) {
        Block block = e.getBlock();
        Location location = block.getLocation();
        Material blockType = block.getType();
        if (blockType == Material.RESPAWN_ANCHOR
                && wgrpContainer.getRsRegion().checkStandingRegion(
                location, config.getRegionProtectMap())) {
            e.setCancelled(true);
        }
    }

}
