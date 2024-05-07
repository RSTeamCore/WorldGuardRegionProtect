package net.ritasister.wgrp.util.config;

import org.jetbrains.annotations.NotNull;

public interface CheckVersionLang<T> {

    void checkVersionLang(T wgrpBukkitBase, final @NotNull Config config);

}
