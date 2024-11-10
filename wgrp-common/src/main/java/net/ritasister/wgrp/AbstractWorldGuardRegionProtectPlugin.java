package net.ritasister.wgrp;

import net.ritasister.wgrp.api.ApiRegistrationUtil;
import net.ritasister.wgrp.api.WorldGuardRegionProtect;

public abstract class AbstractWorldGuardRegionProtectPlugin implements WorldGuardRegionProtectPlugin {

    private WorldGuardRegionProtectApiProvider apiProvider;

    public final void load() {
        //Register API
        this.apiProvider = new WorldGuardRegionProtectApiProvider(this);
        this.apiProvider.ensureApiWasLoadedByPlugin();
        ApiRegistrationUtil.registerProvider(apiProvider);
        registerApiOnPlatform(this.apiProvider);
    }

    public final void unLoad() {

    }

    @Override
    public final WorldGuardRegionProtectApiProvider getApiProvider() {
        return this.apiProvider;
    }

    protected abstract void registerApiOnPlatform(WorldGuardRegionProtect api);

}
