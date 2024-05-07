package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

public class EntityProtect implements Listener {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public EntityProtect(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplodeEntity(@NotNull EntityExplodeEvent e) {
        wgrpBukkitPlugin.getRsApi().entityCheck(e, e.getEntity(), e.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyEntityDamageByEntityEvent(@NotNull EntityDamageByEntityEvent e) {
        wgrpBukkitPlugin.getRsApi().entityCheck(e, e.getDamager(), e.getEntity());
    }

}
