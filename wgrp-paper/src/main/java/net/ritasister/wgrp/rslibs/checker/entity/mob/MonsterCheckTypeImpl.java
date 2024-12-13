package net.ritasister.wgrp.rslibs.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.EntityTypeHelper;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class MonsterCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public MonsterCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        final Monster monster = (Monster) entity;
        final EntityType monsterType = monster.getType();
        return ConfigFields.MONSTER_TYPE.getList(wgrpPlugin.getWgrpPaperBase()).contains(monsterType.name().toLowerCase());
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[]{
            EntityType.GHAST,
            EntityType.VINDICATOR,
            EntityType.GUARDIAN,
            EntityType.CREEPER,
            EntityType.VEX,
            EntityType.CAVE_SPIDER,
            EntityType.EVOKER,
            EntityType.ELDER_GUARDIAN,
            EntityType.HUSK,
            EntityType.WITHER,
            EntityType.MAGMA_CUBE,
            EntityType.SLIME,
            EntityType.ENDERMAN,
            EntityType.SPIDER,
            EntityType.SKELETON,
            EntityType.PIGLIN,
            EntityType.WITHER_SKELETON,
            EntityType.GIANT,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ENDERMITE,
            EntityType.WARDEN,
            EntityType.RAVAGER,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.SILVERFISH,
            EntityType.PHANTOM,
            EntityType.STRAY,
            EntityType.DROWNED,
            EntityType.BLAZE,
            EntityType.ILLUSIONER,
            EntityType.PIGLIN_BRUTE,
            EntityType.ZOMBIE,
            EntityType.SHULKER,
            EntityType.WITCH,
            EntityType.PILLAGER,
            EntityType.ZOGLIN,
            EntityType.ENDER_DRAGON,
            //New mobs since mc 1.21
            EntityTypeHelper.getEntityType("BREEZE"),
            EntityTypeHelper.getEntityType("BOGGED")
        };
    }

}
