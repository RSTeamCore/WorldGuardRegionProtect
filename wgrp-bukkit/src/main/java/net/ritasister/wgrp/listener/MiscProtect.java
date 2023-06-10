package net.ritasister.wgrp.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
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

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MiscProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    @EventHandler(priority = EventPriority.LOW)
    private void denyBlockFromTo(@NotNull BlockFromToEvent e) {
        Block block = e.getBlock();
        Location location = e.getToBlock().getLocation();
        if (wgrpContainer.getRsRegion().checkStandingRegion(location, wgrpContainer.getConfig().getRegionProtectMap())) {
            if (wgrpContainer.getConfig().isDenyWaterFlowToRegion() && block.getType() == Material.WATER
                    || wgrpContainer.getConfig().isDenyLavaFlowToRegion() && block.getType() == Material.LAVA) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyStructureGrow(@NotNull StructureGrowEvent e) {
        if (e.getPlayer() != null) {
            Player player = e.getPlayer();
            if (wgrpContainer.getRsRegion().checkStandingRegion(e.getLocation(), wgrpContainer.getConfig().getRegionProtectMap()
            )) {
                if (!wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
