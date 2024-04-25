package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import net.rsteamcore.config.Container;
import org.jetbrains.annotations.NotNull;

public interface InitMessages {

    Container initMessages(@NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase, Config config);

}
