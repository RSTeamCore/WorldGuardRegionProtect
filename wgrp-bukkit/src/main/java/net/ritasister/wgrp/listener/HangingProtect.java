package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HangingProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    private final Config config;

    public HangingProtect(final @NotNull WGRPContainer wgrpContainer) {
        this.wgrpContainer = wgrpContainer;
        this.config = wgrpContainer.getConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingBreakByEntity(@NotNull HangingBreakByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity attacker = Objects.requireNonNull(e.getRemover());
        Location location = entity.getLocation();
        if (wgrpContainer.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())) {
            if (!(attacker instanceof Player)) {
                switch (entity.getType()) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME, PAINTING -> e.setCancelled(true);
                }
            } else if (!wgrpContainer.getRsApi().isPlayerListenerPermission(
                    (Player) attacker, UtilPermissions.REGION_PROTECT)) {
                switch (entity.getType()) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME, PAINTING -> e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingPlace(@NotNull HangingPlaceEvent e) {
        Entity entity = e.getEntity();
        Player player = e.getPlayer();
        Location loc = entity.getLocation();
        if (wgrpContainer.getRsRegion().checkStandingRegion(loc, config.getRegionProtectMap())) {
            if (!wgrpContainer.getRsApi().isPlayerListenerPermission(
                    Objects.requireNonNull(player),
                    UtilPermissions.REGION_PROTECT
            )) {
                switch (player.getInventory().getItemInMainHand().getType()) {
                    case ITEM_FRAME, GLOW_ITEM_FRAME, PAINTING -> e.setCancelled(true);
                }
            }
        }
    }
}
