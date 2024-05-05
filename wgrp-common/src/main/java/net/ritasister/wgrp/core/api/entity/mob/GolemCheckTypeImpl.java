package net.ritasister.wgrp.core.api.entity.mob;

import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.api.entity.EntityCheckType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Golem;
import org.jetbrains.annotations.NotNull;

public class GolemCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectPlugin wgrpBukkitPlugin;

    public GolemCheckTypeImpl(final WorldGuardRegionProtectPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        Golem golem = (Golem) entity;
        EntityType golemType = golem.getType();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getAnimalType().contains(golemType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
                EntityType.SNOWMAN,
                EntityType.IRON_GOLEM
        };
    }
}
