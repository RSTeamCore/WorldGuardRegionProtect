package net.ritasister.wgrp.rslibs.checker;

import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class EntityCheckTypeProvider {

    private final List<EntityCheckType> entityCheckTypes = new ArrayList<>() {{
        add(new BoatMaterialCheckTypeImpl());
        add(new MinecartMaterialCheckTypeImpl());
    }};

    public EntityCheckType getCheck(Entity entity) {
        return this.entityCheckTypes
                .stream()
                .filter((check) -> check.getEntityType() == entity.getType())
                .findFirst()
                .orElseThrow();
    }

}
