package net.ritasister.wgrp.util.file.config.version;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.version.ConfigVersionReader;
import net.ritasister.wgrp.util.file.UpdateFile;
import net.ritasister.wgrp.util.file.config.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class VersionUpdateService {

    private final ConfigVersionReader<ConfigType, YamlConfiguration> versionReader;
    private final UpdateFile updateFile;

    public VersionUpdateService(ConfigVersionReader<ConfigType, YamlConfiguration> versionReader, UpdateFile updateFile) {
        this.versionReader = versionReader;
        this.updateFile = updateFile;
    }

    public void checkAndUpdate(@NotNull WorldGuardRegionProtectPaperPlugin plugin,
                               @NotNull ConfigType type,
                               @NotNull File currentFile,
                               @NotNull String resourcePath) {

        final InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(plugin.getWgrpPaperBase().getResource(resourcePath)),
                StandardCharsets.UTF_8
        );
        final YamlConfiguration newConfig = YamlConfiguration.loadConfiguration(reader);

        if (!currentFile.exists()) {
            plugin.getLogger().info(type + " file does not exist. Creating default one.");
            update(type, plugin, currentFile, resourcePath);
            return;
        }

        final YamlConfiguration currentConfig = YamlConfiguration.loadConfiguration(currentFile);
        final int newVersion = versionReader.getVersion(type, newConfig);
        final int currentVersion;

        try {
            currentVersion = versionReader.getVersion(type, currentConfig);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warn("Version is null or empty in " + type + " file. Recreating default one.");
            update(type, plugin, currentFile, resourcePath);
            return;
        }

        if (currentVersion >= newVersion) {
            plugin.getLogger().info(type + " file is up to date. No update required.");
            return;
        }

        plugin.getLogger().info("Found new version of " + type + " file, updating...");
        update(type, plugin, currentFile, resourcePath);
    }

    private void update(ConfigType type, WorldGuardRegionProtectPaperPlugin plugin, File file, String resourcePath) {
        if (type == ConfigType.CONFIG) {
            updateFile.updateConfig(plugin, file);
        } else {
            String lang = new File(resourcePath).getName().replace(".yml", "");
            updateFile.updateLang(plugin, file, lang);
        }
    }
}
