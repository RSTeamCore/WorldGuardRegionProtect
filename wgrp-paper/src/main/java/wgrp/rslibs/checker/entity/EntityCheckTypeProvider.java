package wgrp.rslibs.checker.entity;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.rslibs.checker.entity.misc.ArmorStandCheckTypeImpl;
import wgrp.rslibs.checker.entity.misc.ExplosiveCheckTypeImpl;
import wgrp.rslibs.checker.entity.misc.HangingCheckTypeImpl;
import wgrp.rslibs.checker.entity.mob.AllayMobCheckTypeImpl;
import wgrp.rslibs.checker.entity.mob.AmbientCheckTypeImpl;
import wgrp.rslibs.checker.entity.mob.AnimalCheckTypeImpl;
import wgrp.rslibs.checker.entity.mob.GolemCheckTypeImpl;
import wgrp.rslibs.checker.entity.mob.HumanEntityCheckTypeImpl;
import wgrp.rslibs.checker.entity.mob.MonsterCheckTypeImpl;
import wgrp.rslibs.checker.entity.mob.WaterMobCheckTypeImpl;
import wgrp.rslibs.checker.entity.transport.BoatMaterialCheckTypeImpl;
import wgrp.rslibs.checker.entity.transport.MinecartMaterialCheckTypeImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityCheckTypeProvider {

    private final List<EntityCheckType> entityCheckTypes;

    public EntityCheckTypeProvider(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpPlugin) {

        this.entityCheckTypes = new ArrayList<>() {{
            //Ambient
            add(new AmbientCheckTypeImpl(wgrpPlugin));
            //Mobs
            add(new AnimalCheckTypeImpl(wgrpPlugin));
            add(new MonsterCheckTypeImpl(wgrpPlugin));
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
