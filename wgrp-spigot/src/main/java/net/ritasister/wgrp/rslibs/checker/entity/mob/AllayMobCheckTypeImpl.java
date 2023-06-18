package net.ritasister.wgrp.rslibs.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.checker.entity.EntityCheckType;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class AllayMobCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public AllayMobCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        Allay allay = (Allay) entity;
        EntityType allayType = allay.getType();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getAnimalType().contains(allayType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
                EntityType.ALLAY
        };
    }
}
