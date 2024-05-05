package net.ritasister.wgrp.core.api.entity.mob;

import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.api.entity.EntityCheckType;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class AllayMobCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectPlugin worldGuardRegionProtectPlugin;

    public AllayMobCheckTypeImpl(final WorldGuardRegionProtectPlugin worldGuardRegionProtectPlugin) {
        this.worldGuardRegionProtectPlugin = worldGuardRegionProtectPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        Allay allay = (Allay) entity;
        EntityType allayType = allay.getType();
        return worldGuardRegionProtectPlugin.getConfigLoader().getConfig().getAnimalType().contains(allayType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
                EntityType.ALLAY
        };
    }
}
