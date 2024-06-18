package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ConfigCheckVersion implements CheckVersion<WorldGuardRegionProtectBukkitPlugin> {

    private final ParamsVersionCheck<YamlConfiguration> paramsVersionCheck;

    public ConfigCheckVersion(ParamsVersionCheck<YamlConfiguration> paramsVersionCheck) {
        this.paramsVersionCheck = paramsVersionCheck;
    }

    @Override
    public void checkVersion(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        File currentConfigFile = new File(wgrpBukkitPlugin.getWgrpBukkitBase().getDataFolder(), "config.yml");
        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(
                wgrpBukkitPlugin.getWgrpBukkitBase().getResource("config.yml")));
        YamlConfiguration currentVersion = YamlConfiguration.loadConfiguration(currentConfigFile);
        YamlConfiguration newVersion = YamlConfiguration.loadConfiguration(inputStreamReader);
        if (currentConfigFile.exists() && !paramsVersionCheck.getCurrentVersion(currentVersion).equals(Objects.requireNonNull(paramsVersionCheck.getNewVersion(newVersion)))) {
            wgrpBukkitPlugin.getWgrpBukkitBase().getApi().getPluginLogger().info("Found new version of config file, updating this now...");
            Path renameOldLang = new File(
                    wgrpBukkitPlugin.getWgrpBukkitBase().getDataFolder(),
                    "config-old-" + paramsVersionCheck.getSimpleDateFormat() + ".yml").toPath();
            try {
                Files.move(currentConfigFile.toPath(), renameOldLang, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            wgrpBukkitPlugin.getWgrpBukkitBase().saveResource("config.yml", true);
        } else {
            wgrpBukkitPlugin.getPluginLogger().info("No update is required for the config file");
        }
    }

}
