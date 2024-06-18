package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import net.ritasister.wgrp.util.config.Config;
import org.jetbrains.annotations.NotNull;

public interface CheckVersion {

    default void checkVersion(final WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {

    }

    default void checkVersion(final WorldGuardRegionProtectBukkitBase wgrpBukkitBase, final @NotNull Config config) {

    }

}
