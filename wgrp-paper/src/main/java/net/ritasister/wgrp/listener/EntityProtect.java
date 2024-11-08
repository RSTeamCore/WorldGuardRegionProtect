package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Listens of all events for protect entity.
 */
public class EntityProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPaperPlugin;

    public EntityProtect(final WorldGuardRegionProtectPaperPlugin wgrpPaperPlugin) {
        this.wgrpPaperPlugin = wgrpPaperPlugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplodeEntity(@NotNull EntityExplodeEvent e) {
        wgrpPaperPlugin.getRsApi().entityCheck(e, e.getEntity(), e.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyEntityDamageByEntityEvent(@NotNull EntityDamageByEntityEvent e) {
        wgrpPaperPlugin.getRsApi().entityCheck(e, e.getDamager(), e.getEntity());
    }

}
