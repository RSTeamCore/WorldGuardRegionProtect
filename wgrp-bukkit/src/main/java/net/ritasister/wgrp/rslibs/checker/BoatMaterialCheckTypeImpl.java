package net.ritasister.wgrp.rslibs.checker;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BoatMaterialCheckTypeImpl implements EntityCheckType {

    private final Config config;

    @Override
    public boolean check(final Entity entity) {
        Boat boat = (Boat) entity;
        Material boatMaterial = boat.getBoatMaterial();
        return config.getVehicleType().contains(boatMaterial.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {EntityType.CHEST_BOAT, EntityType.BOAT};
    }
}
