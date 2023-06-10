package net.ritasister.wgrp.rslibs.checker;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class BoatMaterialCheckTypeImpl implements EntityCheckType {

    @Override
    public void check(final Entity entity) {

    }

    @Override
    public EntityType getEntityType() {
        return EntityType.BOAT;
    }

}
