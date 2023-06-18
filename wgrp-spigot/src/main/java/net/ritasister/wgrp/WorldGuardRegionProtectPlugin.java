package net.ritasister.wgrp;

import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.rslibs.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.api.WorldGuardRegionProtectProvider;
import net.ritasister.wgrp.rslibs.util.ServerType;
import net.ritasister.wgrp.util.config.loader.ConfigLoader;
import org.jetbrains.annotations.NotNull;

@Slf4j
public abstract class WorldGuardRegionProtectPlugin implements WorldGuardRegionProtect {

    private WorldGuardRegionProtectBukkitBase wgRegionProtectBukkitBase;

    private boolean debug;

    protected WorldGuardRegionProtectPlugin(final @NotNull String version, final ServerType serverType) {
        log.info("You are running on " + serverType);
        WorldGuardRegionProtectProvider.setWorldGuardRegionProtect(this);
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public void setDebug(final boolean debug) {
        this.debug = debug;
    }

    public void disable() {
        ConfigLoader configLoader = new ConfigLoader();
        configLoader.getConfig().saveConfig();
    }

}
