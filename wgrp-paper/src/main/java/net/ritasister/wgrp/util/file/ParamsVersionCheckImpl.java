package net.ritasister.wgrp.util.file;

import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.file.config.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ParamsVersionCheckImpl implements ParamsVersionCheck<ConfigType, YamlConfiguration> {

    public String getDateFormat() {
        final ZonedDateTime now = ZonedDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd-hh.mm.ss");
        return formatter.format(now);
    }

    @Override
    public boolean checkMatches(@NotNull final String version) {
        if (version.isEmpty()) {
            throw new IllegalArgumentException("version cannot be null or empty");
        }
        return !version.matches("^[0-9]+$");
    }

    @Override
    public String getCurrentVersion(ConfigType configType, final @NotNull YamlConfiguration currentYaml) {
        return getStringVersion(configType, currentYaml);
    }

    @Override
    public String getNewVersion(ConfigType configType, final @NotNull YamlConfiguration newYaml) {
        return getStringVersion(configType, newYaml);
    }

    private @NotNull String getStringVersion(final ConfigType configType, @NotNull final YamlConfiguration yamlConfiguration) {
        if (configType == null) {
            throw new IllegalArgumentException("configType cannot be null");
        }
        final String versionKey;
        if (ConfigType.CONFIG.equals(configType)) {
            versionKey = "wgRegionProtect.version";
        } else if (ConfigType.LANG.equals(configType)) {
            versionKey = "langTitle.version";
        } else {
            throw new IllegalArgumentException("Invalid configType: " + configType);
        }
        final String version = yamlConfiguration.getString(versionKey);
        if (version == null || version.trim().isEmpty()) {
            throw new IllegalArgumentException("Value version cannot be null");
        }
        return version.trim();
    }
}
