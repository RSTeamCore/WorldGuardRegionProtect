package net.ritasister.wgrp.util.file.messages;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.config.ConfigType;
import net.ritasister.wgrp.util.file.CheckVersion;
import net.ritasister.wgrp.util.file.config.ConfigFields;
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
    public void checkVersion(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        wgrpPlugin.getLogger().info("Started checking the new version of the language file...");

        final String lang = ConfigFields.LANG.get(wgrpPlugin.getWgrpPaperBase()).toString();

        final File currentLangFile = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(), "lang/" + lang + ".yml");
        final InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(wgrpPlugin.getWgrpPaperBase().getResource("lang/" + lang + ".yml")));

        final YamlConfiguration currentLangVersion = YamlConfiguration.loadConfiguration(currentLangFile);
        final YamlConfiguration newLangVersion = YamlConfiguration.loadConfiguration(reader);

        if (currentLangFile.exists()
                && !paramsVersionCheck.checkMatches(paramsVersionCheck.getCurrentVersion(ConfigType.LANG, currentLangVersion))
                && !paramsVersionCheck.getCurrentVersion(ConfigType.LANG, currentLangVersion)
                .equals(paramsVersionCheck.getNewVersion(ConfigType.LANG, newLangVersion))) {
            wgrpPlugin.getRsApi().updateFile(wgrpPlugin, currentLangFile, ConfigType.LANG, lang);
            wgrpPlugin.getLogger().info("Found new version of lang file, updating this now...");
        } else {
            wgrpPlugin.getLogger().info("No update is required for the lang file");
        }
    }

}
