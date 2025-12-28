package net.ritasister.wgrp.rslibs.api.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.utility.entity.EntityHelper;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Golem;
import org.jetbrains.annotations.Contract;
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
        return ConfigFields.ANIMAL_TYPE.asStringList(wgrpPlugin).contains(golemType.name().toLowerCase());
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[] {
            EntityHelper.getEntityType("SNOWMAN"), //Only available on older version like be 1.20.2
            EntityHelper.getEntityType("SNOW_GOLEM"), //Only available on newest version like be 1.21+
            EntityHelper.getEntityType("COPPER_GOLEM"), // available from since 1.21.9
            EntityType.IRON_GOLEM // Always available because it's been in the game since 1.2.1
        };
    }

}
