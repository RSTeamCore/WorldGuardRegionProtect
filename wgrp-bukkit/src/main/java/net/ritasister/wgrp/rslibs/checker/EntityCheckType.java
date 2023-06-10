package net.ritasister.wgrp.rslibs.checker;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public interface EntityCheckType {

    void check(Entity entity);

    EntityType getEntityType();
}
