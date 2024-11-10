package net.ritasister.wgrp;

import net.ritasister.wgrp.api.ApiRegistrationUtil;

public abstract class AbstractWorldGuardRegionProtectPlugin implements WorldGuardRegionProtectPlugin {

    private WorldGuardRegionProtectApiProvider apiProvider;

    public void load() {
        //Register API
        this.apiProvider = new WorldGuardRegionProtectApiProvider(this);
        ApiRegistrationUtil.registerProvider(apiProvider);
    }

    public void unLoad() {

    }

    @Override
    public final WorldGuardRegionProtectApiProvider getApiProvider() {
        return this.apiProvider;
    }
}
