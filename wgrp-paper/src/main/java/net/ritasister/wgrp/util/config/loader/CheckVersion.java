package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.util.config.Config;
import org.jetbrains.annotations.NotNull;

public interface CheckVersion {

    default void checkVersion(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {

    }

    default void checkVersion(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin, final @NotNull Config config) {

    }

}
