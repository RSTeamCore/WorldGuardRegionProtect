package net.ritasister.wgrp.rslibs.checker;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.misc.ArmorStandCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.misc.ExplosiveCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.misc.HangingCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.mob.AllayMobCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.mob.AmbientCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.mob.AnimalCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.mob.GolemCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.mob.HumanEntityCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.mob.MonsterCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.mob.WaterMobCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.transport.BoatMaterialCheckTypeImpl;
import net.ritasister.wgrp.rslibs.checker.transport.MinecartMaterialCheckTypeImpl;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityCheckTypeProvider {

    private final List<EntityCheckType> entityCheckTypes;

    public EntityCheckTypeProvider(final @NotNull WGRPContainer wgrpContainer) {

        this.entityCheckTypes = new ArrayList<>() {{
            //Ambient
            add(new AmbientCheckTypeImpl(wgrpContainer));
            //Mobs
            add(new AnimalCheckTypeImpl(wgrpContainer));
            add(new MonsterCheckTypeImpl(wgrpContainer));
            add(new WaterMobCheckTypeImpl(wgrpContainer));
            add(new HumanEntityCheckTypeImpl(wgrpContainer));
            add(new AllayMobCheckTypeImpl(wgrpContainer));
            add(new GolemCheckTypeImpl(wgrpContainer));
            //Transport
            add(new BoatMaterialCheckTypeImpl(wgrpContainer));
            add(new MinecartMaterialCheckTypeImpl(wgrpContainer));
            //Misc
            add(new HangingCheckTypeImpl(wgrpContainer));
            add(new ArmorStandCheckTypeImpl(wgrpContainer));
            add(new ExplosiveCheckTypeImpl(wgrpContainer));
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
