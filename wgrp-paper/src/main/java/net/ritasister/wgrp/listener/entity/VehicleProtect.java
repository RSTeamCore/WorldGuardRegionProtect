package net.ritasister.wgrp.listener.entity;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.config.field.ConfigFields;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.jetbrains.annotations.NotNull;

public final class VehicleProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public VehicleProtect(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleCollision(@NotNull VehicleEntityCollisionEvent e) {
        if (!ConfigFields.DENY_COLLISION_WITH_VEHICLE.asBoolean(wgrpPlugin)) {
            return;
        }
        wgrpPlugin.getRsApi().entityCheck(e, e.getEntity(), e.getVehicle());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleEnter(@NotNull VehicleEnterEvent e) {
        if (!ConfigFields.DENY_SIT_AS_PASSENGER_IN_VEHICLE.asBoolean(wgrpPlugin)) {
            return;
        }
        wgrpPlugin.getRsApi().entityCheck(e, e.getEntered(), e.getVehicle());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleDamage(@NotNull VehicleDamageEvent e) {
        if (!ConfigFields.DENY_DAMAGE_VEHICLE.asBoolean(wgrpPlugin)) {
            return;
        }
        wgrpPlugin.getRsApi().entityCheck(e, e.getAttacker(), e.getVehicle());
    }

}
