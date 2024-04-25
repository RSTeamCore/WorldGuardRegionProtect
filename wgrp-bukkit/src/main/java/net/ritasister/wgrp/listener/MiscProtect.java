package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.jetbrains.annotations.NotNull;

public class MiscProtect implements Listener {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private final Config config;

    public MiscProtect(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
    }

    @EventHandler(priority = EventPriority.LOW)
    private void denyBlockFromTo(@NotNull BlockFromToEvent e) {
        Block block = e.getBlock();
        Location location = e.getToBlock().getLocation();
        if (wgrpBukkitPlugin.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())) {
            if (config.isDenyWaterFlowToRegion() && block.getType() == Material.WATER
                    || config.isDenyLavaFlowToRegion() && block.getType() == Material.LAVA) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyStructureGrow(@NotNull StructureGrowEvent e) {
        if (e.getPlayer() != null) {
            Player player = e.getPlayer();
            if (wgrpBukkitPlugin.getRsRegion().checkStandingRegion(e.getLocation(), config.getRegionProtectMap())) {
                if (!wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
