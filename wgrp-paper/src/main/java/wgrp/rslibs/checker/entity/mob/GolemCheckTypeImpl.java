package wgrp.rslibs.checker.entity.mob;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Golem;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.rslibs.checker.entity.EntityCheckType;

public class GolemCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public GolemCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
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
