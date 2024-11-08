package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.util.config.ConfigFields;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Listens of all events for protect hanging, item frame, etc.
 */
public class HangingProtect implements Listener {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public HangingProtect(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingPlace(@NotNull HangingPlaceEvent e) {
        if (!ConfigFields.DENY_PLACE_ITEM_FRAME_OR_PAINTING.getBoolean(wgrpBukkitPlugin.getWgrpBukkitBase())) {
            return;
        }
        wgrpBukkitPlugin.getRsApi().entityCheck(e, e.getEntity(), e.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingBreakByEntity(@NotNull HangingBreakByEntityEvent e) {
        if (!ConfigFields.DENY_DAMAGE_ITEM_FRAME_OR_PAINTING.getBoolean(wgrpBukkitPlugin.getWgrpBukkitBase())) {
            return;
        }
        wgrpBukkitPlugin.getRsApi().entityCheck(e, e.getRemover(), e.getEntity());
    }
}
