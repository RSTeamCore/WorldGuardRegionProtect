package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.ConfigType;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Objects;

public class MessageCheckVersion implements CheckVersion {

    private final ParamsVersionCheck<ConfigType, YamlConfiguration> paramsVersionCheck;

    public MessageCheckVersion(ParamsVersionCheck<ConfigType, YamlConfiguration> paramsVersionCheck) {
        this.paramsVersionCheck = paramsVersionCheck;
    }

    @Override
    public void checkVersion(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin, final @NotNull Config config) {
        wgrpBukkitPlugin.getPluginLogger().info("Started checking the new version of the language file...");
        String lang = config.getLang();
        File currentLangFile = new File(wgrpBukkitPlugin.getWgrpBukkitBase().getDataFolder(), "lang/" + lang + ".yml");
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(
                wgrpBukkitPlugin.getWgrpBukkitBase().getResource("lang/" + lang + ".yml")));
        YamlConfiguration currentLangVersion = YamlConfiguration.loadConfiguration(currentLangFile);
        YamlConfiguration newLangVersion = YamlConfiguration.loadConfiguration(reader);
        if(currentLangFile.exists() && currentLangVersion.getString("langTitle.version") == null) {
            wgrpBukkitPlugin.getRsApi().updateFile(wgrpBukkitPlugin, currentLangFile, ConfigType.LANG, lang);
            wgrpBukkitPlugin.getPluginLogger().info("String version is null...recreating file config...");
        }
        if (currentLangFile.exists() &&
                !paramsVersionCheck.getCurrentVersion(ConfigType.LANG, currentLangVersion)
                .equals(paramsVersionCheck.getNewVersion(ConfigType.LANG, newLangVersion))) {
            wgrpBukkitPlugin.getPluginLogger().info("Found new version of lang file, updating this now...");
            wgrpBukkitPlugin.getRsApi().updateFile(wgrpBukkitPlugin, currentLangFile, ConfigType.LANG, lang);
        } else {
            wgrpBukkitPlugin.getPluginLogger().info("No update is required for the lang file");
        }
    }

}
