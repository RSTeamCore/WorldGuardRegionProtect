package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Objects;

public class ConfigCheckVersion implements CheckVersion {

    private final ParamsVersionCheck<ConfigType, YamlConfiguration> paramsVersionCheck;

    public ConfigCheckVersion(ParamsVersionCheck<ConfigType, YamlConfiguration> paramsVersionCheck) {
        this.paramsVersionCheck = paramsVersionCheck;
    }

    @Override
    public void checkVersion(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        wgrpBukkitPlugin.getPluginLogger().info("Started checking the new version of the config file...");
        File currentConfigFile = new File(wgrpBukkitPlugin.getWgrpBukkitBase().getDataFolder(), "config.yml");
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(
                wgrpBukkitPlugin.getWgrpBukkitBase().getResource("config.yml")));
        YamlConfiguration currentConfigVersion = YamlConfiguration.loadConfiguration(currentConfigFile);
        YamlConfiguration newVersion = YamlConfiguration.loadConfiguration(reader);
        if(currentConfigFile.exists() && currentConfigVersion.getString("wgRegionProtect.version") == null) {
            wgrpBukkitPlugin.getRsApi().updateFile(wgrpBukkitPlugin, currentConfigFile, ConfigType.CONFIG, null);
            wgrpBukkitPlugin.getPluginLogger().info("String version in config file is null...recreating file config...");
        } else if (currentConfigFile.exists() &&
                !paramsVersionCheck.getCurrentVersion(ConfigType.CONFIG, currentConfigVersion)
                .equals(paramsVersionCheck.getNewVersion(ConfigType.CONFIG, newVersion))) {
            wgrpBukkitPlugin.getRsApi().updateFile(wgrpBukkitPlugin, currentConfigFile, ConfigType.CONFIG, null);
            wgrpBukkitPlugin.getWgrpBukkitBase().getApi().getPluginLogger().info("Found new version of config file, updating this now...");
        } else {
            wgrpBukkitPlugin.getPluginLogger().info("No update is required for the config file");
        }
    }

}
