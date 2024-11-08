package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.ConfigType;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.ConfigFields;
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
        final String lang = (String) ConfigFields.getField("LANG").getVariable();
        final File currentLangFile = new File(wgrpBukkitPlugin.getWgrpBukkitBase().getDataFolder(), "lang/" + lang + ".yml");
        final InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(wgrpBukkitPlugin.getWgrpBukkitBase().getResource("lang/" + lang + ".yml")));
        final YamlConfiguration currentLangVersion = YamlConfiguration.loadConfiguration(currentLangFile);
        final YamlConfiguration newLangVersion = YamlConfiguration.loadConfiguration(reader);
        if(currentLangFile.exists() && currentLangVersion.getString("langTitle.version") == null) {
            wgrpBukkitPlugin.getRsApi().updateFile(wgrpBukkitPlugin, currentLangFile, ConfigType.LANG, lang);
            wgrpBukkitPlugin.getPluginLogger().info("String version in lang file is null...recreating lang file...");
        } else if (currentLangFile.exists() &&
                !paramsVersionCheck.getCurrentVersion(ConfigType.LANG, currentLangVersion)
                .equals(paramsVersionCheck.getNewVersion(ConfigType.LANG, newLangVersion))) {
            wgrpBukkitPlugin.getRsApi().updateFile(wgrpBukkitPlugin, currentLangFile, ConfigType.LANG, lang);
            wgrpBukkitPlugin.getPluginLogger().info("Found new version of lang file, updating this now...");
        } else {
            wgrpBukkitPlugin.getPluginLogger().info("No update is required for the lang file");
        }
    }

}
