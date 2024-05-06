package net.ritasister.wgrp.rslibs.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.checker.entity.EntityCheckType;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class AmbientCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public AmbientCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        Ambient ambient = (Ambient) entity;
        EntityType ambientType = ambient.getType();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getInteractType().contains(ambientType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
                EntityType.BAT
        };
    }
}
