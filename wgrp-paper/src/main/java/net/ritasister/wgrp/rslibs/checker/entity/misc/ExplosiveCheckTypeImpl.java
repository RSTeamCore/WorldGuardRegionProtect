package wgrp.rslibs.checker.entity.misc;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.rslibs.checker.entity.EntityCheckType;

public class ExplosiveCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public ExplosiveCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        Explosive explosive = (Explosive) entity;
        EntityType explosiveType = explosive.getType();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getEntityExplodeType().contains(explosiveType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
                EntityType.PRIMED_TNT,
                EntityType.ENDER_CRYSTAL,
                EntityType.MINECART_TNT,
                EntityType.CREEPER,
                EntityType.WITHER_SKULL,
                EntityType.FIREBALL
        };
    }
}
