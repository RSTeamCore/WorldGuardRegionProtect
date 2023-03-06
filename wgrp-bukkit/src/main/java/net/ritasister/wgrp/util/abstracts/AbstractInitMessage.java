package net.ritasister.wgrp.util.abstracts;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.interfaces.InitMessagesImpl;
import net.ritasister.wgrp.util.interfaces.CheckVersionLangImpl;
import net.rsteamcore.config.Container;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractInitMessage implements InitMessagesImpl, CheckVersionLangImpl {

    public abstract Container initMessages(@NotNull final WGRPBukkitPlugin wgrpBukkitPlugin, Config config);

    @Override
    public void checkVersionLang(final WGRPBukkitPlugin wgrpBukkitPlugin, final @NotNull Config config) {}

}
