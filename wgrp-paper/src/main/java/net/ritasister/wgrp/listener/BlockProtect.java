package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.regions.RegionAction;
import net.ritasister.wgrp.rslibs.api.config.Container;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.ConfigFields;
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

/**
 * Listens of all events where player can interact with blocks.
 */
public class BlockProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    private final Config config;
    private final Container messages;

    public BlockProtect(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
        this.config = wgrpPlugin.getConfigLoader().getConfig();
        this.messages = wgrpPlugin.getConfigLoader().getMessages();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyBreak(@NotNull BlockBreakEvent e) {
        final Player player = e.getPlayer();
        final Location location = e.getBlock().getLocation();
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectAllowMap())
                || wgrpPlugin.getRegionAdapter().checkStandingRegion(
                location,
                config.getRegionProtectOnlyBreakAllowMap())) {
            e.setCancelled(false);
        } else if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                && wgrpPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            e.setCancelled(true);
            sendMessage(player);
        } else if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location)
                && wgrpPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.SPY_INSPECT_ADMIN_LISTENER)) {

            spyMethod(e.getBlock(), player, location, RegionAction.BREAK);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlace(@NotNull BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        final Location location = e.getBlock().getLocation();
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectAllowMap())) {
            e.setCancelled(false);
        } else if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                && wgrpPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
            e.setCancelled(true);
            sendMessage(player);
        } else if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location)
                && wgrpPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.SPY_INSPECT_ADMIN_LISTENER)) {

            spyMethod(e.getBlock(), player, location, RegionAction.PLACE);
        }
    }

    private void spyMethod(Block block, @NotNull Player player, Location location, RegionAction regionAction) {
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
                    block.getWorld().getName());
        }
    }

    private void sendMessage(Player player) {
        if (ConfigFields.REGION_MESSAGE_PROTECT.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
            messages.get("messages.ServerMsg.wgrpMsg").send(player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplodeRespawnAnchor(@NotNull BlockExplodeEvent e) {
        final Block block = e.getBlock();
        final Location location = block.getLocation();
        final Material blockType = block.getType();
        if (blockType == Material.RESPAWN_ANCHOR && wgrpPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
<<<<<<< HEAD
    public void denyFormObsidianOrCobblestone(@NotNull BlockFormEvent e) {
=======
    public void onObsidianForm(@NotNull BlockFormEvent e) {
>>>>>>> e5041337f1ef368f99dc249ba6ae293b0d232960
        final Location location = e.getBlock().getLocation();
        if (!ConfigFields.DENY_FORM_BLOCK_FROM_LAVA_AND_WATER.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
            return;
        }
        if (e.getNewState().getType() == Material.OBSIDIAN || e.getNewState().getType() == Material.COBBLESTONE
                && wgrpPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())) {
            e.setCancelled(true);
        }
    }

}
