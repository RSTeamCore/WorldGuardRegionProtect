package net.ritasister.wgrp;

import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.api.WorldGuardRegionProtectProvider;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionMetadata;
import net.ritasister.wgrp.util.ServerType;
import net.ritasister.wgrp.util.Version;

/**
 * Represent class for any platform.
 */
public abstract class WorldGuardRegionProtectPlugin implements WorldGuardRegionProtect {

    protected final Version version;
    private final ServerType serverType;
    private final String prefix;
    private WorldGuardRegionMetadata worldGuardRegionMetadata;
    private Version newestVersion;
    private boolean debug;

    protected WorldGuardRegionProtectPlugin(final String version, final ServerType serverType) {
        this.version = new Version(version);
        this.serverType = serverType;
        this.prefix = "§8[§eWorldGuardRegionProtect" + serverType + "§8] ";
        WorldGuardRegionProtectProvider.setWorldGuardRegionProtect(this);
    }

}
