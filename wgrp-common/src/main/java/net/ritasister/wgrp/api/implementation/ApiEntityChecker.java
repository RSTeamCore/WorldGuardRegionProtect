package net.ritasister.wgrp.api.implementation;

import net.ritasister.wgrp.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.api.model.entity.Entity;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;

public class ApiEntityChecker<T> implements EntityCheckType<T> {

    private final WorldGuardRegionProtectPlugin plugin;

    public ApiEntityChecker(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean check(final Entity entity) {
        return plugin.getEntityChecker().check(entity);
    }

    @Override
    public T[] getEntityType() {
        return (T[]) plugin.getEntityChecker().getEntityType();
    }

}
