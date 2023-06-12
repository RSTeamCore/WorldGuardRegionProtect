package net.ritasister.wgrp.rslibs.checker.mob;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MonsterCheckTypeImpl implements EntityCheckType {

    private final WGRPContainer wgrpContainer;

    @Override
    public boolean check(final Entity entity) {
        Monster monster = (Monster) entity;
        EntityType monsterType = monster.getType();
        return wgrpContainer.getConfig().getMonsterType().contains(monsterType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
                EntityType.GHAST,
                EntityType.VINDICATOR,
                EntityType.GUARDIAN,
                EntityType.CREEPER,
                EntityType.VEX,
                EntityType.HOGLIN,
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
                EntityType.ENDER_DRAGON
        };
    }

}
