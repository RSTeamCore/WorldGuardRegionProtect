package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WGRPContainer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

public class EntityProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    public EntityProtect(final @NotNull WGRPContainer wgrpContainer) {
        this.wgrpContainer = wgrpContainer;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplodeEntity(@NotNull EntityExplodeEvent e) {
        wgrpContainer.getRsApi().entityCheck(e, e.getEntity(), e.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyEntityDamageByEntityEvent(@NotNull EntityDamageByEntityEvent e) {
            wgrpContainer.getRsApi().entityCheck(e, e.getDamager(), e.getEntity());
    }

}
