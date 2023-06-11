package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import net.ritasister.wgrp.rslibs.checker.EntityCheckTypeProvider;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.jetbrains.annotations.NotNull;

public class VehicleProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    private final Config config;

    private final EntityCheckTypeProvider entityCheckTypeProvider;

    public VehicleProtect(final @NotNull WGRPContainer wgrpContainer) {
        this.wgrpContainer = wgrpContainer;
        this.config = wgrpContainer.getConfig();
        this.entityCheckTypeProvider = new EntityCheckTypeProvider(wgrpContainer);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleCollision(@NotNull VehicleEntityCollisionEvent e) {
        if (!config.isDenyCollisionWithVehicle()) {
            return;
        }
        this.entityCheck(e, e.getEntity(), e.getVehicle());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleEnter(@NotNull VehicleEnterEvent e) {
        if (!wgrpContainer.getConfig().isDenySitAsPassengerInVehicle()) {
            return;
        }
        this.entityCheck(e, e.getEntered(), e.getVehicle());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleDamage(@NotNull VehicleDamageEvent e) {
        if (!wgrpContainer.getConfig().isDenyDamageVehicle()) {
            return;
        }
        this.entityCheck(e, e.getAttacker(), e.getVehicle());
    }

    private void entityCheck(Cancellable cancellable, Entity entity, @NotNull Entity vehicle) {
        if (!wgrpContainer.getRsRegion().checkStandingRegion(vehicle.getLocation(), config.getRegionProtectMap())
                || !wgrpContainer.getRsApi().isEntityListenerPermission(entity, UtilPermissions.REGION_PROTECT)) {
            return;
        }
        EntityCheckType entityCheckType = entityCheckTypeProvider.getCheck(vehicle);
        if(entityCheckType.check(vehicle)) {
            cancellable.setCancelled(true);
        }
    }
}
