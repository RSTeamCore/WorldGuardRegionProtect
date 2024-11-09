package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.ConfigType;
import net.ritasister.wgrp.util.config.ConfigFields;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Objects;

public class MessageCheckVersion implements CheckVersion {

    private final ParamsVersionCheck<YamlConfiguration> paramsVersionCheck;

    public MessageCheckVersion(ParamsVersionCheck<YamlConfiguration> paramsVersionCheck) {
        this.paramsVersionCheck = paramsVersionCheck;
    }

    @Override
    public void checkVersion(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        wgrpPlugin.getPluginLogger().info("Started checking the new version of the language file...");
        final String lang = ConfigFields.LANG.get(wgrpPlugin.getWgrpPaperBase()).toString();

        final File currentLangFile = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(), "lang/" + lang + ".yml");
        final InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(wgrpPlugin.getWgrpPaperBase().getResource("lang/" + lang + ".yml")));

        final YamlConfiguration currentLangVersion = YamlConfiguration.loadConfiguration(currentLangFile);
        final YamlConfiguration newLangVersion = YamlConfiguration.loadConfiguration(reader);

        if (currentLangFile.exists()
                && !paramsVersionCheck.checkMatches(paramsVersionCheck.getCurrentVersion(ConfigType.LANG.name(), currentLangVersion))
                && !paramsVersionCheck.getCurrentVersion(ConfigType.LANG.name(), currentLangVersion).equals(paramsVersionCheck.getNewVersion(ConfigType.LANG.name(), newLangVersion))) {
            wgrpPlugin.getRsApi().updateFile(wgrpPlugin, currentLangFile, ConfigType.LANG.name(), lang);
            wgrpPlugin.getPluginLogger().info("Found new version of lang file, updating this now...");
        } else {
            wgrpPlugin.getPluginLogger().info("No update is required for the lang file");
        }
    }

}
