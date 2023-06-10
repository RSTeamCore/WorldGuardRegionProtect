package net.ritasister.wgrp.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.api.RegionAction;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
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

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BlockProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyBreak(@NotNull BlockBreakEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        if (wgrpContainer.getRsRegion().checkStandingRegion(location, wgrpContainer.getConfig().getRegionProtectAllowMap())
                || wgrpContainer.getRsRegion().checkStandingRegion(location, wgrpContainer.getConfig().getRegionProtectOnlyBreakAllowMap())) {
            e.setCancelled(false);
        } else if (wgrpContainer.getRsRegion().checkStandingRegion(location, wgrpContainer.getConfig().getRegionProtectMap())
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
        if (wgrpContainer.getRsRegion().checkStandingRegion(location, wgrpContainer.getConfig().getRegionProtectAllowMap())) {
            e.setCancelled(false);
        } else if (wgrpContainer.getRsRegion().checkStandingRegion(location, wgrpContainer.getConfig().getRegionProtectMap())
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
        if (wgrpContainer.getConfig().getDataBaseEnable()) {
            wgrpContainer.getRsStorage().getDataSource().setLogAction(
                    player.getPlayerProfile().getName(), player.getPlayerProfile().getId(),
                    System.currentTimeMillis(), regionAction,
                    wgrpContainer.getRsRegion().getProtectRegionName(location), block.getWorld().getName(), block.getX(),
                    block.getY(), block.getZ());
        }
    }

    private void sendMessage(Player player) {
        if (wgrpContainer.getConfig().getRegionMessageProtect()) {
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
                location, wgrpContainer.getConfig().getRegionProtectMap())) {
            e.setCancelled(true);
        }
    }

}
