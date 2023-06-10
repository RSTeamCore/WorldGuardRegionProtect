package net.ritasister.wgrp.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import net.ritasister.wgrp.rslibs.checker.EntityCheckTypeProvider;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Location;
import org.bukkit.Material;
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

    private final Config config = wgrpContainer.getConfig();

    private final EntityCheckTypeProvider entityCheckTypeProvider = new EntityCheckTypeProvider();

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleCollision(@NotNull VehicleEntityCollisionEvent e) {
        if (!config.isDenyCollisionWithVehicle()) {
            return;
        }
        Entity entity = e.getEntity();
        Entity vehicle = e.getVehicle();
        Location vehicleLocation = e.getVehicle().getLocation();
        if (!wgrpContainer.getRsRegion().checkStandingRegion(vehicleLocation, config.getRegionProtectMap())
                || !wgrpContainer.getRsApi().isEntityListenerPermission(entity, UtilPermissions.REGION_PROTECT)) {
            return;
        }
        EntityCheckType entityCheckType = entityCheckTypeProvider.getCheck(vehicle);
        entityCheckType.check(entity);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleEnter(@NotNull VehicleEnterEvent e) {
        if (!wgrpContainer.getConfig().isDenySitAsPassengerInVehicle()) {
            return;
        }
        Entity entered = e.getEntered();
        Entity vehicle = e.getEntered().getVehicle();
        Location vehicleLocation = e.getVehicle().getLocation();
        if (entered instanceof Player) {
            if (wgrpContainer.getRsRegion().checkStandingRegion(vehicleLocation, wgrpContainer.getConfig().getRegionProtectMap())
                    && wgrpContainer.getRsApi().isEntityListenerPermission(entered, UtilPermissions.REGION_PROTECT)) {
                if (vehicle instanceof Boat boat && vehicle instanceof Minecart minecart) {
                    Material boatMaterial = boat.getBoatMaterial();
                    Material minecartMaterial = minecart.getMinecartMaterial();
                    for (String vehicleType : wgrpContainer.getConfig().getVehicleType()) {
                        if (boatMaterial == Material.getMaterial(vehicleType.toUpperCase())
                                || minecartMaterial == Material.getMaterial(vehicleType.toUpperCase())) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleDamage(@NotNull VehicleDamageEvent e) {
        if (wgrpContainer.getConfig().isDenyDamageVehicle()) {
            return;
        }
        Entity attacker = e.getAttacker();
        Location vehicleLocation = e.getVehicle().getLocation();
        if (attacker instanceof Player) {
            if (wgrpContainer.getRsRegion().checkStandingRegion(vehicleLocation, wgrpContainer.getConfig().getRegionProtectMap())
                    && wgrpContainer.getRsApi().isEntityListenerPermission(attacker, UtilPermissions.REGION_PROTECT)) {
                if (attacker instanceof Boat boat && attacker instanceof Minecart minecart) {
                    Material boatMaterial = boat.getBoatMaterial();
                    Material minecartMaterial = minecart.getMinecartMaterial();
                    for (String vehicleType : wgrpContainer.getConfig().getVehicleType()) {
                        if (boatMaterial == Material.getMaterial(vehicleType.toUpperCase())
                                || minecartMaterial == Material.getMaterial(vehicleType.toUpperCase())) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

}
