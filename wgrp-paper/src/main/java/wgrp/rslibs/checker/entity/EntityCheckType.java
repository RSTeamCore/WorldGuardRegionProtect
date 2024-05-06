package wgrp.rslibs.checker.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public interface EntityCheckType {

    boolean check(Entity entity);

    EntityType[] getEntityType();
}
