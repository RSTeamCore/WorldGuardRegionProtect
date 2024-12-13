package net.ritasister.wgrp.rslibs.checker.entity.transport;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.EntityTypeHelper;
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
                EntityTypeHelper.getEntityType("CHEST_BOAT"),
                EntityTypeHelper.getEntityType("BOAT"),
                //Since after mc 1.21.2+
                EntityTypeHelper.getEntityType("ACACIA_BOAT"),
                EntityTypeHelper.getEntityType("ACACIA_CHEST_BOAT"),
                EntityTypeHelper.getEntityType("BAMBOO_RAFT"),
                EntityTypeHelper.getEntityType("BAMBOO_CHEST_RAFT"),
                EntityTypeHelper.getEntityType("BIRCH_BOAT"),
                EntityTypeHelper.getEntityType("BIRCH_CHEST_BOAT"),
                EntityTypeHelper.getEntityType("CHERRY_BOAT"),
                EntityTypeHelper.getEntityType("CHERRY_CHEST_BOAT"),
                EntityTypeHelper.getEntityType("DARK_OAK_BOAT"),
                EntityTypeHelper.getEntityType("DARK_OAK_CHEST_BOAT"),
                EntityTypeHelper.getEntityType("JUNGLE_BOAT"),
                EntityTypeHelper.getEntityType("JUNGLE_CHEST_BOAT"),
                EntityTypeHelper.getEntityType("MANGROVE_BOAT"),
                EntityTypeHelper.getEntityType("MANGROVE_CHEST_BOAT"),
                EntityTypeHelper.getEntityType("OAK_BOAT"),
                EntityTypeHelper.getEntityType("OAK_CHEST_BOAT"),
                EntityTypeHelper.getEntityType("PALE_OAK_BOAT"),
                EntityTypeHelper.getEntityType("PALE_OAK_CHEST_BOAT"),
                EntityTypeHelper.getEntityType("SPRUCE_BOAT"),
                EntityTypeHelper.getEntityType("SPRUCE_CHEST_BOAT"),

        };
    }

}
