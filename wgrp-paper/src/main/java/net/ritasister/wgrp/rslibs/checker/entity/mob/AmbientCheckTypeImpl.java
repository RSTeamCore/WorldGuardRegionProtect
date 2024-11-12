package net.ritasister.wgrp.rslibs.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public final class AmbientCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public AmbientCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        final Ambient ambient = (Ambient) entity;
        final EntityType ambientType = ambient.getType();
        return ConfigFields.INTERACT_TYPE.getList(wgrpPlugin.getWgrpPaperBase()).contains(ambientType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
            EntityType.BAT
        };
    }

}
