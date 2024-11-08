package net.ritasister.wgrp.rslibs.checker.entity.misc;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.config.ConfigFields;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.jetbrains.annotations.NotNull;

public final class ExplosiveCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public ExplosiveCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        final Explosive explosive = (Explosive) entity;
        final EntityType explosiveType = explosive.getType();
        return ConfigFields.ENTITY_EXPLODE_TYPE.getList(wgrpPlugin.getWgrpPaperBase()).contains(explosiveType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
            EntityType.TNT,
            EntityType.END_CRYSTAL,
            EntityType.TNT_MINECART,
            EntityType.CREEPER,
            EntityType.WITHER_SKULL,
            EntityType.FIREBALL
        };
    }

}
