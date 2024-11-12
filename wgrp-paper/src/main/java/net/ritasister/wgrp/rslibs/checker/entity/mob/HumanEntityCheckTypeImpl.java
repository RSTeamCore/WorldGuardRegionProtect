package net.ritasister.wgrp.rslibs.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

public final class HumanEntityCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public HumanEntityCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        final HumanEntity humanEntity = (HumanEntity) entity;
        final EntityType humanEntityType = humanEntity.getType();
        return ConfigFields.INTERACT_TYPE.getList(wgrpPlugin.getWgrpPaperBase()).contains(humanEntityType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
            EntityType.PLAYER
        };
    }

}
