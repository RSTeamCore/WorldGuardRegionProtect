package net.ritasister.wgrp.util.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ParamsVersionCheckImpl implements ParamsVersionCheck {

    @NotNull
    public String getSimpleDateFormat() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss");
        return simpleDateFormat.format(date);
    }

    @NotNull
    public String getCurrentVersion(final @NotNull YamlConfiguration currentYaml) {
        return Objects.requireNonNull(currentYaml.getString("langTitle.version"))
                .replaceAll("\"", "")
                .replaceAll("'", "");
    }

    @NotNull
    public String getNewVersion(final @NotNull YamlConfiguration yamlConfiguration) {
        return Objects.requireNonNull(yamlConfiguration.getString("langTitle.version"))
                .replaceAll("\"", "")
                .replaceAll("'", "");
    }
}
