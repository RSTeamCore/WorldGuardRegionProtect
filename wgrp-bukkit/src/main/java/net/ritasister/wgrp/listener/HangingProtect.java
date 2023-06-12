package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.jetbrains.annotations.NotNull;

public class HangingProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    private final Config config;

    public HangingProtect(final @NotNull WGRPContainer wgrpContainer) {
        this.wgrpContainer = wgrpContainer;
        this.config = wgrpContainer.getConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingPlace(@NotNull HangingPlaceEvent e) {
        if (!config.isDenyPlaceItemFrameOrPainting()) {
            return;
        }
        wgrpContainer.getRsApi().entityCheck(e, e.getPlayer(), e.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingBreakByEntity(@NotNull HangingBreakByEntityEvent e) {
        if (!config.isDenyDamageItemFrameOrPainting()) {
            return;
        }
        wgrpContainer.getRsApi().entityCheck(e, e.getRemover(), e.getEntity());
    }
}
