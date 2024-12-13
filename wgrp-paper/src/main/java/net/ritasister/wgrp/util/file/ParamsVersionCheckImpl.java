package net.ritasister.wgrp.util.file;

import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.config.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParamsVersionCheckImpl implements ParamsVersionCheck<ConfigType, YamlConfiguration> {

    public String getSimpleDateFormat() {
        final Date date = new Date();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss");
        return simpleDateFormat.format(date);
    }

    @Override
    public String getCurrentVersion(ConfigType configType, final @NotNull YamlConfiguration currentYaml) {
        return getStringVersion(configType, currentYaml);
    }

    @Override
    public String getNewVersion(ConfigType configType, final @NotNull YamlConfiguration newYaml) {
        return getStringVersion(configType, newYaml);
    }

    @Override
    public boolean checkMatches(@NotNull final String version) {
        return version.matches("^[1-9].*");
    }

    private @NotNull String getStringVersion(final ConfigType configType, @NotNull final YamlConfiguration yamlConfiguration) {
        if (ConfigType.CONFIG.equals(configType)) {
            return yamlConfiguration.getString("wgRegionProtect.version")
                    .replaceAll("\"", "")
                    .replaceAll("'", "");
        } else if (ConfigType.LANG.equals(configType)) {
            return yamlConfiguration.getString("langTitle.version")
                    .replaceAll("\"", "")
                    .replaceAll("'", "");
        }
        throw new RuntimeException("Value version cannot be null!");
    }
}
