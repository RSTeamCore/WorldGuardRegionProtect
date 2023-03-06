package net.ritasister.wgrp.util.interfaces;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.util.config.Config;
import org.jetbrains.annotations.NotNull;

public interface CheckVersionLangImpl {

    void checkVersionLang(WGRPBukkitPlugin wgrpBukkitPlugin, final @NotNull Config config);

}
