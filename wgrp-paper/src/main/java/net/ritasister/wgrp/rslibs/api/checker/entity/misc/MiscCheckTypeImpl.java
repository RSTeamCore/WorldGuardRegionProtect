package net.ritasister.wgrp.rslibs.api.checker.entity.misc;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import net.ritasister.wgrp.util.utility.entity.EntityHelper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class MiscCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpBukkitPlugin;

    public MiscCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        final Item projectile = (Item) entity;
        final EntityType projectileType = projectile.getType();
        return ConfigFields.MISC_ENTITY_TYPE.asStringList(wgrpBukkitPlugin).contains(projectileType.name().toLowerCase());
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[] {
            EntityType.EGG,
            EntityType.ENDER_PEARL,
            EntityType.EXPERIENCE_ORB,
            EntityType.SNOWBALL,
            EntityType.DRAGON_FIREBALL,
            EntityType.FIREBALL,
            EntityType.SMALL_FIREBALL,
            EntityType.WITHER_SKULL,
            EntityType.FALLING_BLOCK,
            EntityType.ARROW,
            EntityType.SPECTRAL_ARROW,
            EntityHelper.getEntityType("DROPPED_ITEM"),
        };
    }

}
