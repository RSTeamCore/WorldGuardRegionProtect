package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Objects;

public class ConfigCheckVersion implements CheckVersion {

    private final ParamsVersionCheck<YamlConfiguration> paramsVersionCheck;

    public ConfigCheckVersion(ParamsVersionCheck<YamlConfiguration> paramsVersionCheck) {
        this.paramsVersionCheck = paramsVersionCheck;
    }

    @Override
    public void checkVersion(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        wgrpPlugin.getPluginLogger().info("Started checking the new version of the config file...");
        final File currentConfigFile = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(), "config.yml");
        final InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(
                wgrpPlugin.getWgrpPaperBase().getResource("config.yml")));
        final YamlConfiguration currentConfigVersion = YamlConfiguration.loadConfiguration(currentConfigFile);
        final YamlConfiguration newVersion = YamlConfiguration.loadConfiguration(reader);
        if(currentConfigFile.exists() && currentConfigVersion.getString("wgRegionProtect.version") == null) {
            wgrpPlugin.getRsApi().updateFile(wgrpPlugin, currentConfigFile, ConfigType.CONFIG.name(), null);
            wgrpPlugin.getPluginLogger().info("String version in config file is null...recreating file config...");
        } else if (currentConfigFile.exists() &&
                !paramsVersionCheck.getCurrentVersion(ConfigType.CONFIG.name(), currentConfigVersion)
                .equals(paramsVersionCheck.getNewVersion(ConfigType.CONFIG.name(), newVersion))) {
            wgrpPlugin.getRsApi().updateFile(wgrpPlugin, currentConfigFile, ConfigType.CONFIG.name(), null);
            wgrpPlugin.getPluginLogger().info("Found new version of config file, updating this now...");
        } else {
            wgrpPlugin.getPluginLogger().info("No update is required for the config file");
        }
    }

}
