package net.ritasister.wgrp.rslibs.api.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.utility.entity.EntityHelper;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class AnimalCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public AnimalCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        final Animals animals = (Animals) entity;
        final EntityType animalsType = animals.getType();
        return ConfigFields.ANIMAL_TYPE.getList(wgrpPlugin.getWgrpPaperBase()).contains(animalsType.name().toLowerCase());
    }

    @Contract(" -> new")
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[] {
            //Misc creature
            EntityType.VILLAGER,
            //Water creature
            EntityType.AXOLOTL,
            //Land creature
            EntityType.DONKEY,
            EntityType.PARROT,
            EntityType.TRADER_LLAMA,
            EntityType.HORSE,
            EntityType.POLAR_BEAR,
            EntityType.OCELOT,
            EntityType.CAT,
            EntityType.FROG,
            EntityType.COW,
            EntityType.FOX,
            EntityHelper.getEntityType("MUSHROOM_COW"), //Only available on older version like be 1.20.2
            EntityType.ZOMBIE_HORSE,
            EntityType.PANDA,
            EntityType.BEE,
            EntityType.SKELETON_HORSE,
            EntityType.PIG,
            EntityType.LLAMA,
            EntityType.GOAT,
            EntityType.WOLF,
            EntityType.CHICKEN,
            EntityType.SNIFFER,
            EntityType.SHEEP,
            EntityType.RABBIT,
            EntityType.TURTLE,
            EntityType.WANDERING_TRADER,
            EntityType.MULE,
            EntityType.TADPOLE,
            EntityType.CAMEL,
            EntityType.HOGLIN,
            EntityHelper.getEntityType("ARMADILLO"), // available from since 1.20.5
            //Only available on newest version like be 1.21+
            EntityHelper.getEntityType("HAPPY_GHAST"), // available from since 1.21.6
        };
    }

}
