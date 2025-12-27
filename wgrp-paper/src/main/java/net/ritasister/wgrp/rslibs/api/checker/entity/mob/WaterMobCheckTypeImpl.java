package net.ritasister.wgrp.rslibs.api.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import net.ritasister.wgrp.util.utility.entity.EntityHelper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WaterMob;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class WaterMobCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public WaterMobCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        final WaterMob waterMob = (WaterMob) entity;
        final EntityType waterMobType = waterMob.getType();
        return ConfigFields.WATER_MOB_TYPE.asStringList(wgrpPlugin.getWgrpPaperBase()).contains(waterMobType.name().toLowerCase());
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[] {
            //Water creature
            EntityType.SQUID,
            EntityType.DOLPHIN,
            //Underground water creature
            EntityType.GLOW_SQUID,
            //Water ambient
            EntityType.COD,
            EntityType.TROPICAL_FISH,
            EntityType.SALMON,
            EntityType.PUFFERFISH,
            EntityHelper.getEntityType("NAUTILUS"), // available from since 1.21.11
        };
    }

}
