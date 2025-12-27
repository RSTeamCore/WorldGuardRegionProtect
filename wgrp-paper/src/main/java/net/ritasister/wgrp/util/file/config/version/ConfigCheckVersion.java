package net.ritasister.wgrp.util.file.config.version;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.api.config.VersionChecker;
import net.ritasister.wgrp.util.file.UpdateFile;
import net.ritasister.wgrp.util.file.config.ConfigType;
import net.ritasister.wgrp.util.utility.VersionCheck;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class ConfigCheckVersion implements VersionChecker<WorldGuardRegionProtectPaperPlugin> {

    private final ParamsVersionCheck<ConfigType, YamlConfiguration> paramsVersionCheck;
    private final UpdateFile updateFile;

    public ConfigCheckVersion(ParamsVersionCheck<ConfigType, YamlConfiguration> paramsVersionCheck, UpdateFile updateFile) {
        this.paramsVersionCheck = paramsVersionCheck;
        this.updateFile = updateFile;
    }

    @Override
    public void check(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        wgrpPlugin.getLogger().info("Started checking the new version of the config file...");

        final File currentConfigFile = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(), "config.yml");
        final InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(wgrpPlugin.getWgrpPaperBase().getResource("config.yml")), StandardCharsets.UTF_8);

        final YamlConfiguration currentConfigVersion = YamlConfiguration.loadConfiguration(currentConfigFile);
        final YamlConfiguration newVersion = YamlConfiguration.loadConfiguration(reader);

        boolean isMustUpdate = true;
        if(currentConfigFile.exists() && paramsVersionCheck.checkMatches(paramsVersionCheck.getCurrentVersion(ConfigType.CONFIG, currentConfigVersion))) {
            wgrpPlugin.getLogger().warn("Field of version in config is invalid. We recreate a new one config replacing corrupted config.");
            updateFile.updateConfig(wgrpPlugin, currentConfigFile);
            isMustUpdate = false;
        }
        if(isMustUpdate && currentConfigFile.exists()
                && !paramsVersionCheck.getCurrentVersion(ConfigType.CONFIG, currentConfigVersion)
                .equals(paramsVersionCheck.getNewVersion(ConfigType.CONFIG, newVersion))) {
            updateFile.updateConfig(wgrpPlugin, currentConfigFile);
            wgrpPlugin.getLogger().info("Found new version of config file, updating this now...");
        } else {
            wgrpPlugin.getLogger().info("No update is required for the config file.");
        }
    }

}
