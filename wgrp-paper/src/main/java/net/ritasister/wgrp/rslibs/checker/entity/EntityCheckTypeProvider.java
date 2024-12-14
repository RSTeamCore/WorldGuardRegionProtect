package net.ritasister.wgrp.rslibs.checker.entity;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.rslibs.checker.entity.misc.ArmorStandCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.misc.ExplosiveCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.misc.HangingCheckTypeImpl;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityCheckTypeProvider {

    private final List<EntityCheckType<Entity, EntityType>> entityCheckTypes;

    public EntityCheckTypeProvider(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.entityCheckTypes = new ArrayList<>() {
            {
                add(new AmbientCheckTypeImpl(wgrpPlugin));
                //Mobs
                add(new AnimalCheckTypeImpl(wgrpPlugin));
                add(new MonsterCheckTypeImpl(wgrpPlugin));
                add(new EnemyCheckTypeImpl(wgrpPlugin));
                add(new WaterMobCheckTypeImpl(wgrpPlugin));
                add(new HumanEntityCheckTypeImpl(wgrpPlugin));
                add(new AllayMobCheckTypeImpl(wgrpPlugin));
                add(new GolemCheckTypeImpl(wgrpPlugin));
                //Transport
                add(new BoatMaterialCheckTypeImpl(wgrpPlugin));
                add(new MinecartMaterialCheckTypeImpl(wgrpPlugin));
                //Misc
                add(new HangingCheckTypeImpl(wgrpPlugin));
                add(new ArmorStandCheckTypeImpl(wgrpPlugin));
                add(new ExplosiveCheckTypeImpl(wgrpPlugin));
            }
        };
    }

    public EntityCheckType<Entity, EntityType> getCheck(Entity entity) {
        return this.entityCheckTypes
                .stream()
                .filter(check -> Arrays.asList(check.getEntityType()).contains(entity.getType()))
                .findFirst()
                .orElseThrow();
    }

}
