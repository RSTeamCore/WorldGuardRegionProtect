package net.ritasister.wgrp.core.util.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

public interface ParamsVersionCheck {

    @NotNull String getSimpleDateFormat();

    @NotNull String getCurrentVersion(final @NotNull YamlConfiguration currentYaml);

    @NotNull String getNewVersion(final @NotNull YamlConfiguration yamlConfiguration);

}
