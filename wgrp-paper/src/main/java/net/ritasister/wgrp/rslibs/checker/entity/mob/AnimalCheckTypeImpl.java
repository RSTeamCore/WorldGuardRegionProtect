package net.ritasister.wgrp.rslibs.checker.entity.mob;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class AnimalCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public AnimalCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final @NotNull Entity entity) {
        Animals animals = (Animals) entity;
        EntityType animalsType = animals.getType();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getAnimalType().contains(animalsType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
                //Misc
                EntityType.VILLAGER,
                //Axolotls
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
                EntityType.MOOSHROOM,
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
                EntityType.CAMEL
        };
    }


}
