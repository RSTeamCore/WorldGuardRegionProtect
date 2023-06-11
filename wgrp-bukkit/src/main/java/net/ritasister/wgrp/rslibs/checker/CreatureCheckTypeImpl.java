package net.ritasister.wgrp.rslibs.checker;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CreatureCheckTypeImpl implements EntityCheckType {

    private final Config config;

    @Override
    public boolean check(final Entity entity) {
        Creature creature = (Creature) entity;
        EntityType creatureType = creature.getType();
        return config.getCreatureType().contains(creatureType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
                //Axolotls
                EntityType.AXOLOTL,
                //Water creature
                EntityType.SQUID,
                EntityType.DOLPHIN,
                //Underground water creature
                EntityType.GLOW_SQUID,
                //Water ambient
                EntityType.COD,
                EntityType.TROPICAL_FISH,
                EntityType.SALMON,
                EntityType.PUFFERFISH,
                //Land creature
                EntityType.DONKEY,
                EntityType.PARROT,
                EntityType.ALLAY,
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
