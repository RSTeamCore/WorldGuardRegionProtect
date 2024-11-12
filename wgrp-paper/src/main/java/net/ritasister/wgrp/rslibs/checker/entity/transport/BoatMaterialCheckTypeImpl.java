package net.ritasister.wgrp.rslibs.checker.entity.transport;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public final class BoatMaterialCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public BoatMaterialCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        final Boat boat = (Boat) entity;
        final Material boatMaterial = boat.getBoatMaterial();
        return ConfigFields.VEHICLE_TYPE.getList(wgrpPlugin.getWgrpPaperBase()).contains(boatMaterial.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
            EntityType.CHEST_BOAT,
            EntityType.BOAT
        };
    }

}
