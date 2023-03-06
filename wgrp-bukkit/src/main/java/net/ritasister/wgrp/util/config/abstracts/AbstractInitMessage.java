package net.ritasister.wgrp.util.config.abstracts;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.impl.ParamsVersionCheckImpl;
import net.ritasister.wgrp.util.config.interfaces.InitMessages;
import net.ritasister.wgrp.util.config.interfaces.CheckVersionLang;
import net.rsteamcore.config.Container;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractInitMessage extends ParamsVersionCheckImpl implements InitMessages, CheckVersionLang {

    public abstract Container initMessages(@NotNull final WGRPBukkitPlugin wgrpBukkitPlugin, Config config);

    @Override
    public abstract void checkVersionLang(final WGRPBukkitPlugin wgrpBukkitPlugin, final @NotNull Config config);

}
