package net.ritasister.wgrp.rslibs.api.checker.entity.misc;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.utility.entity.EntityHelper;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.jetbrains.annotations.Contract;
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
        return ConfigFields.ENTITY_EXPLODE_TYPE.asStringList(wgrpPlugin.getWgrpPaperBase()).contains(explosiveType.name().toLowerCase());
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[] {
            //Only available on older version like be 1.20.2
            EntityHelper.getEntityType("PRIMED_TNT"),
            EntityHelper.getEntityType("ENDER_CRYSTAL"),
            EntityHelper.getEntityType("MINECART_TNT"),
            //Only available on newest version like be 1.21+
            EntityHelper.getEntityType("TNT"),
            EntityHelper.getEntityType("END_CRYSTAL"),
            EntityHelper.getEntityType("TNT_MINECART"),
            EntityType.CREEPER,
            EntityType.WITHER_SKULL,
            EntityType.FIREBALL
        };
    }

}
