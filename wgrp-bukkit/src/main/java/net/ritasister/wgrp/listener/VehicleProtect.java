package net.ritasister.wgrp.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VehicleProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleCollision(@NotNull VehicleEntityCollisionEvent e) {
        if (wgrpContainer.getConfig().isDenyCollisionWithVehicle()) {
            Entity entity = e.getEntity();
            Entity vehicle = e.getVehicle();
            if (entity instanceof Player) {
                if (wgrpContainer.getRsRegion().checkStandingRegion(vehicle.getLocation(), this.wgrpContainer.getConfig().getRegionProtectMap())
                        && wgrpContainer.getRsApi().isEntityListenerPermission(entity, UtilPermissions.REGION_PROTECT)) {
                    if (vehicle instanceof Minecart || vehicle instanceof Boat) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleEnter(@NotNull VehicleEnterEvent e) {
        if (wgrpContainer.getConfig().isDenySitAsPassengerInVehicle()) {
            Entity vehicle = e.getVehicle();
            Entity entered = e.getEntered();
            if (entered instanceof Player) {
                if (wgrpContainer.getRsRegion().checkStandingRegion(vehicle.getLocation(), wgrpContainer.getConfig().getRegionProtectMap())
                        && wgrpContainer.getRsApi().isEntityListenerPermission(entered, UtilPermissions.REGION_PROTECT)) {
                    if (vehicle instanceof Minecart || vehicle instanceof Boat) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleDamage(@NotNull VehicleDamageEvent e) {
        if (wgrpContainer.getConfig().isDenyDamageVehicle()) {
            Entity vehicle = e.getVehicle();
            Entity attacker = e.getAttacker();
            if (attacker instanceof Player) {
                if (wgrpContainer.getRsRegion().checkStandingRegion(vehicle.getLocation(), wgrpContainer.getConfig().getRegionProtectMap())
                        && wgrpContainer.getRsApi().isEntityListenerPermission(attacker, UtilPermissions.REGION_PROTECT)) {
                    if (vehicle instanceof Minecart || vehicle instanceof Boat) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }


}
