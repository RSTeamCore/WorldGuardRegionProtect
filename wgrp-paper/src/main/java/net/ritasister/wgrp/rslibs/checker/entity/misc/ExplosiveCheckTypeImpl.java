package net.ritasister.wgrp.rslibs.checker.entity.misc;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.checker.entity.EntityCheckType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.jetbrains.annotations.NotNull;

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
                EntityType.TNT,
                EntityType.END_CRYSTAL,
                EntityType.TNT_MINECART,
                EntityType.CREEPER,
                EntityType.WITHER_SKULL,
                EntityType.FIREBALL
        };
    }
}
