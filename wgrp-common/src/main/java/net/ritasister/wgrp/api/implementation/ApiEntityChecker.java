package net.ritasister.wgrp.api.implementation;

import net.ritasister.wgrp.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;

public class ApiEntityChecker<E, T> implements EntityCheckType<E, T> {

    private final WorldGuardRegionProtectPlugin plugin;

    public ApiEntityChecker(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean check(final E entity) {
        return plugin.getEntityChecker().check(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] getEntityType() {
        return (T[]) plugin.getEntityChecker().getEntityType();
    }

}
