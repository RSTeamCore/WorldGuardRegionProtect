package net.ritasister.wgrp.listener.entity;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.jetbrains.annotations.NotNull;

public final class EntityProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public EntityProtect(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplodeEntity(@NotNull EntityExplodeEvent event) {
        wgrpPlugin.getRsApi().entityCheck(event, event.getEntity(), event.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyEntityDamageByEntityEvent(@NotNull EntityDamageByEntityEvent event) {
        wgrpPlugin.getRsApi().entityCheck(event, event.getDamager(), event.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denySpawnEntity(@NotNull CreatureSpawnEvent event) {
        if (ConfigFields.DENY_CREATURE_SPAWN.asBoolean(wgrpPlugin.getWgrpPaperBase())) {
            wgrpPlugin.getRsApi().entityCheck(event, event.getEntity(), event.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denySpawnEntity(@NotNull SpawnerSpawnEvent event) {
        if(ConfigFields.DENY_MOB_SPAWN_FROM_SPAWNER.asBoolean(wgrpPlugin.getWgrpPaperBase())) {
            wgrpPlugin.getRsApi().entityCheck(event, event.getEntity(), event.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyItemSpawn(@NotNull ItemSpawnEvent event) {
        if (ConfigFields.DENY_MOB_NATURALLY_SPAWN.asBoolean(wgrpPlugin.getWgrpPaperBase())) {
            wgrpPlugin.getRsApi().entityCheck(event, event.getEntity(), event.getEntity());
        }
    }

}
