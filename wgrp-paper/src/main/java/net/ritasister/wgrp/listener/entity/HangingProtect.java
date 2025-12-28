package net.ritasister.wgrp.listener.entity;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.jetbrains.annotations.NotNull;

public final class HangingProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public HangingProtect(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingPlace(@NotNull HangingPlaceEvent e) {
        if (!ConfigFields.DENY_PLACE_ITEM_FRAME_OR_PAINTING.asBoolean(wgrpPlugin)) {
            return;
        }
        wgrpPlugin.getRsApi().entityCheck(e, e.getEntity(), e.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyHangingBreakByEntity(@NotNull HangingBreakByEntityEvent e) {
        if (!ConfigFields.DENY_DAMAGE_ITEM_FRAME_OR_PAINTING.asBoolean(wgrpPlugin)) {
            return;
        }
        wgrpPlugin.getRsApi().entityCheck(e, e.getRemover(), e.getEntity());
    }
}
