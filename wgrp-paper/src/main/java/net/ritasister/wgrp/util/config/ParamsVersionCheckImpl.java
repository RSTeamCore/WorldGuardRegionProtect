package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParamsVersionCheckImpl implements ParamsVersionCheck<YamlConfiguration> {

    public String getSimpleDateFormat() {
        final Date date = new Date();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss");
        return simpleDateFormat.format(date);
    }

    @Override
    public String getCurrentVersion(String configType, final @NotNull YamlConfiguration currentYaml) {
        return getStringVersion(configType, currentYaml);
    }

    @Override
    public String getNewVersion(String configType, final @NotNull YamlConfiguration newYaml) {
        return getStringVersion(configType, newYaml);
    }

    private @NotNull String getStringVersion(final String configType, @NotNull final YamlConfiguration yamlConfiguration) {
        if (ConfigType.CONFIG.name().equals(configType)) {
            return yamlConfiguration.getString("wgRegionProtect.version")
                    .replaceAll("\"", "")
                    .replaceAll("'", "");
        } else if (ConfigType.LANG.name().equals(configType)) {
            return yamlConfiguration.getString("langTitle.version")
                    .replaceAll("\"", "")
                    .replaceAll("'", "");
        }
        throw new RuntimeException("Value version cannot be null!");
    }
}
