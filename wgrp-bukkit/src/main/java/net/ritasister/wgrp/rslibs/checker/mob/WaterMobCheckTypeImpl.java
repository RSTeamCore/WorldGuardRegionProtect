package net.ritasister.wgrp.rslibs.checker.mob;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WaterMob;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WaterMobCheckTypeImpl implements EntityCheckType {

    private final WGRPContainer wgrpContainer;

    @Override
    public boolean check(final Entity entity) {
        WaterMob waterMob = (WaterMob) entity;
        EntityType waterMobType = waterMob.getType();
        return wgrpContainer.getConfig().getWaterMobType().contains(waterMobType.name().toLowerCase());
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
