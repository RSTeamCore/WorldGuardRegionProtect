package net.ritasister.wgrp.rslibs.checker.mob;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class AnimalCheckTypeImpl implements EntityCheckType {

    private final WGRPContainer wgrpContainer;

    @Override
    public boolean check(final @NotNull Entity entity) {
        Animals animals = (Animals) entity;
        EntityType animalsType = animals.getType();
        return wgrpContainer.getConfig().getAnimalType().contains(animalsType.name().toLowerCase());
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
                EntityType.MUSHROOM_COW,
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
