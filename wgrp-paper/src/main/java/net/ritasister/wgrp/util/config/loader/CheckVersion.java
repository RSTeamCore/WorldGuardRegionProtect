package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.config.Config;
import org.jetbrains.annotations.NotNull;

public interface CheckVersion {

    default void checkVersion(final WorldGuardRegionProtectPaperPlugin wgrpBukkitPlugin) {

    }

    default void checkVersion(final WorldGuardRegionProtectPaperPlugin wgrpBukkitPlugin, final @NotNull Config config) {

    }

}
