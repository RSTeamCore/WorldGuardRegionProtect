package net.ritasister.wgrp.rslibs.checker.entity;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.checker.entity.misc.ArmorStandCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.misc.ExplosiveCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.misc.HangingCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.AllayMobCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.AmbientCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.AnimalCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.GolemCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.HumanEntityCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.MonsterCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.mob.WaterMobCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.transport.BoatMaterialCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.entity.transport.MinecartMaterialCheckTypeImpl;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityCheckTypeProvider {

    private final List<EntityCheckType> entityCheckTypes;

    public EntityCheckTypeProvider(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {

        this.entityCheckTypes = new ArrayList<>() {{
            //Ambient
            add(new AmbientCheckTypeImpl(wgrpBukkitPlugin));
            //Mobs
            add(new AnimalCheckTypeImpl(wgrpBukkitPlugin));
            add(new MonsterCheckTypeImpl(wgrpBukkitPlugin));
            add(new WaterMobCheckTypeImpl(wgrpBukkitPlugin));
            add(new HumanEntityCheckTypeImpl(wgrpBukkitPlugin));
            add(new AllayMobCheckTypeImpl(wgrpBukkitPlugin));
            add(new GolemCheckTypeImpl(wgrpBukkitPlugin));
            //Transport
            add(new BoatMaterialCheckTypeImpl(wgrpBukkitPlugin));
            add(new MinecartMaterialCheckTypeImpl(wgrpBukkitPlugin));
            //Misc
            add(new HangingCheckTypeImpl(wgrpBukkitPlugin));
            add(new ArmorStandCheckTypeImpl(wgrpBukkitPlugin));
            add(new ExplosiveCheckTypeImpl(wgrpBukkitPlugin));
        }};
    }

    public EntityCheckType getCheck(Entity entity) {
        return this.entityCheckTypes
                .stream()
                .filter((check) -> Arrays.asList(check.getEntityType()).contains(entity.getType()))
                .findFirst()
                .orElseThrow();
    }

}
