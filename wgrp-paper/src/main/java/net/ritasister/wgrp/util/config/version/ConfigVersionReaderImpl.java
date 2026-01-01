package net.ritasister.wgrp.util.config.version;

import net.ritasister.wgrp.api.config.version.ConfigVersionReader;
import net.ritasister.wgrp.util.file.config.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

public class ConfigVersionReaderImpl implements ConfigVersionReader<ConfigType, YamlConfiguration> {

    @Override
    public int getVersion(@NotNull ConfigType type, @NotNull YamlConfiguration config) {
        final String key = switch (type) {
            case CONFIG -> "wgRegionProtect.version";
            case LANG -> "langTitle.version";
        };
        final String versionStr = config.getString(key);
        if (versionStr == null || versionStr.isBlank()) {
            throw new IllegalArgumentException("Version cannot be null or empty");
        }
        try {
            return Integer.parseInt(versionStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Version is not a valid number: " + versionStr, e);
        }
    }

}
