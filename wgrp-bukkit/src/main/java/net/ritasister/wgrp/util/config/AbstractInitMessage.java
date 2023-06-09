package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.rsteamcore.config.Container;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractInitMessage extends ParamsVersionCheckImpl implements InitMessages, CheckVersionLang {

    public abstract Container initMessages(@NotNull final WGRPBukkitPlugin wgrpBukkitPlugin, Config config);

    @Override
    public abstract void checkVersionLang(final WGRPBukkitPlugin wgrpBukkitPlugin, final @NotNull Config config);

}
