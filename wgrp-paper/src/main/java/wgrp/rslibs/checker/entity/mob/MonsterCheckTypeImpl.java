package wgrp.rslibs.checker.entity.mob;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.rslibs.checker.entity.EntityCheckType;

public class MonsterCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public MonsterCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        Monster monster = (Monster) entity;
        EntityType monsterType = monster.getType();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getMonsterType().contains(monsterType.name().toLowerCase());
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
