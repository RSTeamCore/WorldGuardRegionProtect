package net.ritasister.wgrp.rslibs.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Golem;
import org.jetbrains.annotations.NotNull;

public final class GolemCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public GolemCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        final Golem golem = (Golem) entity;
        final EntityType golemType = golem.getType();
        return ConfigFields.ANIMAL_TYPE.getList(wgrpPlugin.getWgrpPaperBase()).contains(golemType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
            EntityType.SNOW_GOLEM,
            EntityType.IRON_GOLEM
        };
    }

}
