package wgrp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.util.config.Config;

public class HangingProtect implements Listener {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private final Config config;

    public HangingProtect(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingPlace(@NotNull HangingPlaceEvent e) {
        if (!config.isDenyPlaceItemFrameOrPainting()) {
            return;
        }
        wgrpBukkitPlugin.getRsApi().entityCheck(e, e.getEntity(), e.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingBreakByEntity(@NotNull HangingBreakByEntityEvent e) {
        if (!config.isDenyDamageItemFrameOrPainting()) {
            return;
        }
        wgrpBukkitPlugin.getRsApi().entityCheck(e, e.getRemover(), e.getEntity());
    }
}
