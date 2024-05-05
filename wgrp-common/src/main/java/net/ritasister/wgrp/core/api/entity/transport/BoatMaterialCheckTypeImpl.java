package net.ritasister.wgrp.core.api.entity.transport;

import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.api.entity.EntityCheckType;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class BoatMaterialCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectPlugin wgrpBukkitPlugin;

    public BoatMaterialCheckTypeImpl(final WorldGuardRegionProtectPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        Boat boat = (Boat) entity;
        Material boatMaterial = boat.getBoatMaterial();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getVehicleType().contains(boatMaterial.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
                EntityType.CHEST_BOAT,
                EntityType.BOAT
        };
    }
}
