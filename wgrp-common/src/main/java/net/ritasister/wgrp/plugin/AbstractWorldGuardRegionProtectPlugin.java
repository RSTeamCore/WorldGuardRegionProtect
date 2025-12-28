package net.ritasister.wgrp.plugin;

import net.ritasister.wgrp.WorldGuardRegionProtectApiProvider;
import net.ritasister.wgrp.api.ApiRegistrationUtil;
import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.api.logging.PluginLogger;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractWorldGuardRegionProtectPlugin implements WorldGuardRegionProtectPlugin {

    private WorldGuardRegionProtectApiProvider apiProvider;

    public final void load() {
        //Register WGRP API
        this.apiProvider = new WorldGuardRegionProtectApiProvider(this);
        this.apiProvider.ensureApiWasLoadedByPlugin();
        ApiRegistrationUtil.registerProvider(apiProvider);
        registerApiOnPlatform(this.apiProvider);
    }

    public final void unLoad() {
        // unregister api
        ApiRegistrationUtil.unregisterProvider();
    }

    @Override
    public final WorldGuardRegionProtectApiProvider getApiProvider() {
        return this.apiProvider;
    }

    @Override
    public @NotNull PluginLogger getLogger() {
        return getBootstrap().getPluginLogger();
    }

    protected abstract void registerApiOnPlatform(WorldGuardRegionProtect api);

}
