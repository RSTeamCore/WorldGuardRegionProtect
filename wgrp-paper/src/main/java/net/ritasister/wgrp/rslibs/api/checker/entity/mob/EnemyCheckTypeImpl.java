package net.ritasister.wgrp.rslibs.api.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class EnemyCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public EnemyCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        final Enemy enemy = (Enemy) entity;
        final EntityType monsterType = enemy.getType();
        return ConfigFields.ENEMY_ENTITY_TYPE.getList(wgrpPlugin.getWgrpPaperBase()).contains(monsterType.name().toLowerCase());
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[]{
            EntityType.GHAST,
            EntityType.SLIME,
            EntityType.MAGMA_CUBE,
            EntityType.PHANTOM
        };
    }

}
