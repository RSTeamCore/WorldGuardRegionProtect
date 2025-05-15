package net.ritasister.wgrp.util.file;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.file.config.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class UpdateFile {

    private final ParamsVersionCheck<ConfigType, YamlConfiguration> paramsVersionCheck;

    public UpdateFile(ParamsVersionCheck<ConfigType, YamlConfiguration> paramsVersionCheck) {
        this.paramsVersionCheck = paramsVersionCheck;
    }

    public void updateFile(@NotNull final WorldGuardRegionProtectPaperPlugin wgrpPlugin,
                           final @NotNull File currentFile, ConfigType configType, String lang) {
        if(ConfigType.CONFIG.equals(configType)) {
            final Path renameOldFile = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(),
                    "config-old-" + paramsVersionCheck.getDateFormat() + ".yml").toPath();
            try {
                Files.move(currentFile.toPath(), renameOldFile, StandardCopyOption.REPLACE_EXISTING);
                wgrpPlugin.getLogger().info("Old config file is renamed to: " + renameOldFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            wgrpPlugin.getWgrpPaperBase().saveResource("config.yml", true);
        } else if(ConfigType.LANG.equals(configType)) {
            final Path renameOldLang = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(),
                    "lang/" + lang + "-old-" + paramsVersionCheck.getDateFormat() + ".yml").toPath();
            try {
                Files.move(currentFile.toPath(), renameOldLang, StandardCopyOption.REPLACE_EXISTING);
                wgrpPlugin.getLogger().info("Old lang file is renamed to: " + renameOldLang);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            wgrpPlugin.getWgrpPaperBase().saveResource("lang/" + lang + ".yml", true);
        }
    }

}
