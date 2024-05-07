package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.jetbrains.annotations.NotNull;

public class VehicleProtect implements Listener {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private final Config config;

    public VehicleProtect(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleCollision(@NotNull VehicleEntityCollisionEvent e) {
        if (!config.isDenyCollisionWithVehicle()) {
            return;
        }
        wgrpBukkitPlugin.getRsApi().entityCheck(e, e.getEntity(), e.getVehicle());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleEnter(@NotNull VehicleEnterEvent e) {
        if (!config.isDenySitAsPassengerInVehicle()) {
            return;
        }
        wgrpBukkitPlugin.getRsApi().entityCheck(e, e.getEntered(), e.getVehicle());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyVehicleDamage(@NotNull VehicleDamageEvent e) {
        if (!config.isDenyDamageVehicle()) {
            return;
        }
        wgrpBukkitPlugin.getRsApi().entityCheck(e, e.getAttacker(), e.getVehicle());
    }

}
