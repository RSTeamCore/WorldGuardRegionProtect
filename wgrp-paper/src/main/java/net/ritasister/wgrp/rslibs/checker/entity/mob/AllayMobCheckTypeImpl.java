package net.ritasister.wgrp.rslibs.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class AllayMobCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectBukkitPlugin worldGuardRegionProtectPlugin;

    public AllayMobCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin worldGuardRegionProtectPlugin) {
        this.worldGuardRegionProtectPlugin = worldGuardRegionProtectPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        final Allay allay = (Allay) entity;
        final EntityType allayType = allay.getType();
        return worldGuardRegionProtectPlugin.getConfigLoader().getConfig().getAnimalType().contains(allayType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
            EntityType.ALLAY
        };
    }

}
