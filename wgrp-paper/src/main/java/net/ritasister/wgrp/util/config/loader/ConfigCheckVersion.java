package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
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

public class ConfigCheckVersion implements CheckVersion {

    private final ParamsVersionCheck<YamlConfiguration> paramsVersionCheck;

    public ConfigCheckVersion(ParamsVersionCheck<YamlConfiguration> paramsVersionCheck) {
        this.paramsVersionCheck = paramsVersionCheck;
    }

    @Override
    public void checkVersion(final @NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        File currentConfigFile = new File(wgrpBukkitBase.getDataFolder(), "config.yml");
        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(wgrpBukkitBase
                .getResource("config.yml")));
        YamlConfiguration currentVersion = YamlConfiguration.loadConfiguration(currentConfigFile);
        YamlConfiguration newVersion = YamlConfiguration.loadConfiguration(inputStreamReader);
        if (currentConfigFile.exists() && !paramsVersionCheck.getCurrentVersion(currentVersion).equals(Objects.requireNonNull(paramsVersionCheck.getNewVersion(newVersion)))) {
            wgrpBukkitBase.getApi().getPluginLogger().info("Found new version of config file, updating this now...");
            Path renameOldLang = new File(
                    wgrpBukkitBase.getDataFolder(),
                    "config-old-" + paramsVersionCheck.getSimpleDateFormat() + ".yml").toPath();
            try {
                Files.move(currentConfigFile.toPath(), renameOldLang, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            wgrpBukkitBase.saveResource("config.yml", true);
        } else {
            wgrpBukkitBase.getApi().getPluginLogger().info("No update is required for the config file");
        }
    }

}
