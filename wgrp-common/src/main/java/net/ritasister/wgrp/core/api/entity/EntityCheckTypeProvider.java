package net.ritasister.wgrp.core.api.entity;

import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.api.entity.misc.ArmorStandCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.misc.ExplosiveCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.misc.HangingCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.mob.AllayMobCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.mob.AmbientCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.mob.AnimalCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.mob.GolemCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.mob.HumanEntityCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.mob.MonsterCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.mob.WaterMobCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.transport.BoatMaterialCheckTypeImpl;
import net.ritasister.wgrp.core.api.entity.transport.MinecartMaterialCheckTypeImpl;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityCheckTypeProvider {

    private final List<EntityCheckType> entityCheckTypes;

    public EntityCheckTypeProvider(final @NotNull WorldGuardRegionProtectPlugin wgrpPlugin) {

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
