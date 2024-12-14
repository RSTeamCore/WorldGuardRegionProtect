package net.ritasister.wgrp.rslibs.checker.entity.transport;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.utility.entity.EntityHelper;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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

    @Contract(" -> new")
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[]{
                //Since before mc 1.21.2+
                EntityHelper.getEntityType("CHEST_BOAT"),
                EntityHelper.getEntityType("BOAT"),
                //Since after mc 1.21.2+
                EntityHelper.getEntityType("ACACIA_BOAT"),
                EntityHelper.getEntityType("ACACIA_CHEST_BOAT"),
                EntityHelper.getEntityType("BAMBOO_RAFT"),
                EntityHelper.getEntityType("BAMBOO_CHEST_RAFT"),
                EntityHelper.getEntityType("BIRCH_BOAT"),
                EntityHelper.getEntityType("BIRCH_CHEST_BOAT"),
                EntityHelper.getEntityType("CHERRY_BOAT"),
                EntityHelper.getEntityType("CHERRY_CHEST_BOAT"),
                EntityHelper.getEntityType("DARK_OAK_BOAT"),
                EntityHelper.getEntityType("DARK_OAK_CHEST_BOAT"),
                EntityHelper.getEntityType("JUNGLE_BOAT"),
                EntityHelper.getEntityType("JUNGLE_CHEST_BOAT"),
                EntityHelper.getEntityType("MANGROVE_BOAT"),
                EntityHelper.getEntityType("MANGROVE_CHEST_BOAT"),
                EntityHelper.getEntityType("OAK_BOAT"),
                EntityHelper.getEntityType("OAK_CHEST_BOAT"),
                EntityHelper.getEntityType("PALE_OAK_BOAT"),
                EntityHelper.getEntityType("PALE_OAK_CHEST_BOAT"),
                EntityHelper.getEntityType("SPRUCE_BOAT"),
                EntityHelper.getEntityType("SPRUCE_CHEST_BOAT"),

        };
    }

}
