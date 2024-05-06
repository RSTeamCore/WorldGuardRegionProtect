package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.core.RegionActionImpl;
import net.ritasister.wgrp.core.api.config.Container;
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
import wgrp.util.config.Config;

public class BlockProtect implements Listener {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private final Config config;
    private final Container messages;

    public BlockProtect(@NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
        this.messages = wgrpBukkitPlugin.getConfigLoader().getMessages();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyBreak(@NotNull BlockBreakEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectAllowMap())
                || wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(
                location,
                config.getRegionProtectOnlyBreakAllowMap()
        )) {
            e.setCancelled(false);
        } else if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            e.setCancelled(true);
            sendMessage(player);
        } else if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(location)
                && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.SPY_INSPECT_ADMIN_LISTENER)) {

            spyMethod(e.getBlock(), player, location, RegionActionImpl.BREAK);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlace(@NotNull BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectAllowMap())) {
            e.setCancelled(false);
        } else if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            e.setCancelled(true);
            sendMessage(player);
        } else if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(location)
                && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.SPY_INSPECT_ADMIN_LISTENER)) {

            spyMethod(e.getBlock(), player, location, RegionActionImpl.PLACE);
        }
    }

    private void spyMethod(Block block, @NotNull Player player, Location location, RegionActionImpl regionAction) {
        if (wgrpBukkitPlugin.getSpyLog().contains(player.getPlayerProfile().getUniqueId())) {
            wgrpBukkitPlugin.getRsApi().notifyIfActionInRegion(
                    player,
                    player,
                    player.getPlayerProfile().getName(),
                    regionAction,
                    wgrpBukkitPlugin.getRegionAdapter().getProtectRegionName(location),
                    block.getX(),
                    block.getY(),
                    block.getZ(),
                    block.getWorld().getName()
            );
        }
        if (config.getDataBaseEnable()) {
            wgrpBukkitPlugin.getRsStorage().getDataSource().setLogAction(
                    player.getPlayerProfile().getName(),
                    player.getPlayerProfile().getUniqueId(),
                    System.currentTimeMillis(),
                    regionAction,
                    wgrpBukkitPlugin.getRegionAdapter().getProtectRegionName(location),
                    block.getWorld().getName(),
                    block.getX(),
                    block.getY(),
                    block.getZ());
        }
    }

    private void sendMessage(Player player) {
        if (config.getRegionMessageProtect()) {
            messages.get("messages.ServerMsg.wgrpMsg").send(player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplodeRespawnAnchor(@NotNull BlockExplodeEvent e) {
        Block block = e.getBlock();
        Location location = block.getLocation();
        Material blockType = block.getType();
        if (blockType == Material.RESPAWN_ANCHOR && wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(
                location,
                config.getRegionProtectMap()
        )) {
            e.setCancelled(true);
        }
    }

}
