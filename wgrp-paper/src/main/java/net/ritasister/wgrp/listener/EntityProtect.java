package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.entity.Egg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.jetbrains.annotations.NotNull;

public class EntityProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPaperPlugin;

    public EntityProtect(final WorldGuardRegionProtectPaperPlugin wgrpPaperPlugin) {
        this.wgrpPaperPlugin = wgrpPaperPlugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplodeEntity(@NotNull EntityExplodeEvent event) {
        wgrpPaperPlugin.getRsApi().entityCheck(event, event.getEntity(), event.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyEntityDamageByEntityEvent(@NotNull EntityDamageByEntityEvent event) {
        wgrpPaperPlugin.getRsApi().entityCheck(event, event.getDamager(), event.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denySpawnEntity(@NotNull CreatureSpawnEvent event) {
        if(ConfigFields.DENY_CREATURE_SPAWN.getBoolean(wgrpPaperPlugin.getWgrpPaperBase())) {
            wgrpPaperPlugin.getRsApi().entityCheck(event, event.getEntity(), event.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denySpawnEntity(@NotNull SpawnerSpawnEvent event) {
        if(ConfigFields.DENY_MOB_SPAWN_FROM_SPAWNER.getBoolean(wgrpPaperPlugin.getWgrpPaperBase())) {
            wgrpPaperPlugin.getRsApi().entityCheck(event, event.getEntity(), event.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denySpawnEntity(@NotNull EntitySpawnEvent event){
        if (ConfigFields.DENY_MOB_NATURALLY_SPAWN.getBoolean(wgrpPaperPlugin.getWgrpPaperBase())) {
            wgrpPaperPlugin.getRsApi().entityCheck(event, event.getEntity(), event.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyItemSpawn(@NotNull ItemSpawnEvent event) {
        if(event.getEntity() instanceof Egg) {
            if (ConfigFields.DENY_MOB_NATURALLY_SPAWN.getBoolean(wgrpPaperPlugin.getWgrpPaperBase())) {
                wgrpPaperPlugin.getRsApi().entityCheck(event, event.getEntity(), event.getEntity());
            }
        }
    }

}
