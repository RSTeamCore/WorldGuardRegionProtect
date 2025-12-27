package net.ritasister.wgrp.listener.miscellaneous;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
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

public final class MiscProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public MiscProtect(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    private void denyBlockFromTo(@NotNull BlockFromToEvent e) {
        final Block block = e.getBlock();
        final Location location = e.getToBlock().getLocation();
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, wgrpPlugin.getConfigProvider().getConfig().getRegionProtectMap())) {
            if (ConfigFields.DENY_WATER_FLOW_TO_REGION.asBoolean(wgrpPlugin.getWgrpPaperBase())
                    && block.getType() == Material.WATER
                    || ConfigFields.DENY_LAVA_FLOW_TO_REGION.asBoolean(wgrpPlugin.getWgrpPaperBase())
                    && block.getType() == Material.LAVA) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyStructureGrow(@NotNull StructureGrowEvent e) {
        if (e.getPlayer() != null) {
            final Player player = e.getPlayer();
            if (wgrpPlugin.getRegionAdapter().checkStandingRegion(e.getLocation(), wgrpPlugin.getConfigProvider().getConfig().getRegionProtectMap())) {
                if (!wgrpPlugin.getPermissionCheck().hasPlayerPermission(player, UtilPermissions.REGION_PROTECT)) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
