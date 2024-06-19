package net.ritasister.wgrp.rslibs.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WaterMob;

public class WaterMobCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public WaterMobCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        final WaterMob waterMob = (WaterMob) entity;
        final EntityType waterMobType = waterMob.getType();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getWaterMobType().contains(waterMobType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
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
        };
    }

}
