package net.ritasister.wgrp.rslibs.checker.entity;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.rslibs.checker.entity.misc.ArmorStandCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.misc.ExplosiveCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.misc.HangingCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.misc.MiscCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.AllayMobCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.AmbientCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.AnimalCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.EnemyCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.GolemCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.HumanEntityCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.MonsterCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.WaterMobCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.transport.BoatMaterialCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.transport.MinecartMaterialCheckTypeImpl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class EntityCheckTypeProvider {

    private final List<EntityCheckType<Entity, EntityType>> entityCheckTypes;

    public EntityCheckTypeProvider(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.entityCheckTypes = Arrays.asList(
                // Mobs
                new AmbientCheckTypeImpl(wgrpPlugin),
                new AnimalCheckTypeImpl(wgrpPlugin),
                new MonsterCheckTypeImpl(wgrpPlugin),
                new EnemyCheckTypeImpl(wgrpPlugin),
                new WaterMobCheckTypeImpl(wgrpPlugin),
                new HumanEntityCheckTypeImpl(wgrpPlugin),
                new AllayMobCheckTypeImpl(wgrpPlugin),
                new GolemCheckTypeImpl(wgrpPlugin),
                // Transport
                new BoatMaterialCheckTypeImpl(wgrpPlugin),
                new MinecartMaterialCheckTypeImpl(wgrpPlugin),
                // Misc
                new HangingCheckTypeImpl(wgrpPlugin),
                new ArmorStandCheckTypeImpl(wgrpPlugin),
                new ExplosiveCheckTypeImpl(wgrpPlugin),
                new MiscCheckTypeImpl(wgrpPlugin)
        );
    }

    public EntityCheckType<Entity, EntityType> getCheck(Entity entity) {
        return this.entityCheckTypes
                .stream()
                .filter(check -> Arrays.asList(check.getEntityType()).contains(entity.getType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Failed to find EntityCheckType for entity type " + entity.getType()));
    }

}
