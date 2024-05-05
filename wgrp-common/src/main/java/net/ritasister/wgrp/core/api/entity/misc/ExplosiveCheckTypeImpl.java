package net.ritasister.wgrp.core.api.entity.misc;

import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.api.entity.EntityCheckType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.jetbrains.annotations.NotNull;

public class ExplosiveCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectPlugin wgrpBukkitPlugin;

    public ExplosiveCheckTypeImpl(final WorldGuardRegionProtectPlugin wgrpBukkitPlugin) {
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
