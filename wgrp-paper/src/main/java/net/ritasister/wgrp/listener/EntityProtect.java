package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.jetbrains.annotations.NotNull;

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

    @EventHandler(priority = EventPriority.LOWEST)
    private void denySpawnEntity(@NotNull CreatureSpawnEvent e) {
        if(ConfigFields.DENY_CREATURE_SPAWN.getBoolean(wgrpPaperPlugin.getWgrpPaperBase())) {
            wgrpPaperPlugin.getRsApi().entityCheck(e, e.getEntity(), e.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denySpawnEntity(@NotNull SpawnerSpawnEvent e) {
        if(ConfigFields.DENY_MOB_SPAWN_FROM_SPAWNER.getBoolean(wgrpPaperPlugin.getWgrpPaperBase())) {
            wgrpPaperPlugin.getRsApi().entityCheck(e, e.getEntity(), e.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denySpawnEntity(@NotNull EntitySpawnEvent e){
        if (ConfigFields.DENY_MOB_NATURALLY_SPAWN.getBoolean(wgrpPaperPlugin.getWgrpPaperBase())) {
            wgrpPaperPlugin.getRsApi().entityCheck(e, e.getEntity(), e.getEntity());
        }
    }

}
