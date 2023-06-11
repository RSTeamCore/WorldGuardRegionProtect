package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

public class EntityProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    private final Config config;

    public EntityProtect(final @NotNull WGRPContainer wgrpContainer) {
        this.wgrpContainer = wgrpContainer;
        this.config = wgrpContainer.getConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyExplodeEntity(@NotNull EntityExplodeEvent e) {
        Entity entity = e.getEntity();
        EntityType entityType = e.getEntityType();
        Location loc = entity.getLocation();
        if (wgrpContainer.getRsRegion().checkStandingRegion(loc, config.getRegionProtectMap())) {
            if (config.getExplodeEntity()) {
                switch (entityType) {
                    case PRIMED_TNT, ENDER_CRYSTAL, MINECART_TNT, CREEPER, FIREBALL, WITHER_SKULL -> e.blockList().clear();
                    default -> e.setCancelled(true);
                }
            } else {
                switch (entityType) {
                    case PRIMED_TNT, ENDER_CRYSTAL, MINECART_TNT, CREEPER, FIREBALL, WITHER_SKULL -> e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyEntityDamageByEntityEvent(@NotNull EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity attacker = e.getDamager();
        Location location = entity.getLocation();
        if (wgrpContainer.getRsRegion().checkStandingRegion(location, config.getRegionProtectMap())) {
            if (!(attacker instanceof Player)) {
                switch (entity.getType()) {
                    case ARMOR_STAND, ENDER_CRYSTAL, ITEM_FRAME, GLOW_ITEM_FRAME, TROPICAL_FISH, AXOLOTL,
                            TURTLE, FOX, SNIFFER, CAMEL -> e.setCancelled(true);
                }
            } else if (!wgrpContainer.getRsApi().isPlayerListenerPermission(
                    (Player) attacker, UtilPermissions.REGION_PROTECT)) {
                switch (entity.getType()) {
                    case ARMOR_STAND, ENDER_CRYSTAL, ITEM_FRAME, GLOW_ITEM_FRAME, TROPICAL_FISH, AXOLOTL,
                            TURTLE, FOX, SNIFFER, CAMEL -> e.setCancelled(true);
                }
            }
        }
    }

}
