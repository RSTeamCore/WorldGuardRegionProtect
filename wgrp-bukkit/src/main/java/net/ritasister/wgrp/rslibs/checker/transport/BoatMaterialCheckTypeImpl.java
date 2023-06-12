package net.ritasister.wgrp.rslibs.checker.transport;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BoatMaterialCheckTypeImpl implements EntityCheckType {

    private final WGRPContainer wgrpContainer;

    @Override
    public boolean check(final Entity entity) {
        Boat boat = (Boat) entity;
        Material boatMaterial = boat.getBoatMaterial();
        return wgrpContainer.getConfig().getVehicleType().contains(boatMaterial.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
                EntityType.CHEST_BOAT,
                EntityType.BOAT
        };
    }
}
