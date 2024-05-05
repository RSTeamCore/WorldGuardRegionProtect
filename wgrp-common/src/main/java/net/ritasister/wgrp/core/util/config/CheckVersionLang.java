package net.ritasister.wgrp.core.util.config;

import org.jetbrains.annotations.NotNull;

public interface CheckVersionLang<T> {

    void checkVersionLang(T wgrpBukkitBase, final @NotNull Config config);

}
