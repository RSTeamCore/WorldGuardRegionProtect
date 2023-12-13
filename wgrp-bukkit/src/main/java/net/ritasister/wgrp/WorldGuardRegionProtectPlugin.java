package net.ritasister.wgrp;

import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.rslibs.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.api.WorldGuardRegionProtectProvider;
import net.ritasister.wgrp.rslibs.util.ServerType;

@Slf4j
public abstract class WorldGuardRegionProtectPlugin implements WorldGuardRegionProtect {

    protected WorldGuardRegionProtectPlugin(final ServerType serverType) {
        log.info("You are running on " + serverType);
        WorldGuardRegionProtectProvider.setWorldGuardRegionProtect(this);
    }

    public void disable() {
        //ConfigLoader configLoader = new ConfigLoader();
        //configLoader.getConfig().saveConfig();
    }

}
