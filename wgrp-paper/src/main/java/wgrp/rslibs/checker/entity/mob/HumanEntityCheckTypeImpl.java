package wgrp.rslibs.checker.entity.mob;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.rslibs.checker.entity.EntityCheckType;

public class HumanEntityCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public HumanEntityCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        HumanEntity humanEntity = (HumanEntity) entity;
        EntityType humanEntityType = humanEntity.getType();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getInteractType().contains(humanEntityType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
                EntityType.PLAYER
        };
    }
}
