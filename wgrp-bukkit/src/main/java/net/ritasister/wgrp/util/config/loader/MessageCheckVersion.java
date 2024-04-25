package net.ritasister.wgrp.util.config.loader;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import net.ritasister.wgrp.util.config.CheckVersionLang;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.ParamsVersionCheck;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class MessageCheckVersion implements CheckVersionLang {

    private final ParamsVersionCheck paramsVersionCheck;

    @Override
    @SneakyThrows
    public void checkVersionLang(final @NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase, final @NotNull Config config) {
        String lang = config.getLang();
        File currentLangFile = new File(wgrpBukkitBase.getDataFolder(), "lang/" + lang + ".yml");
        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(wgrpBukkitBase.getResource("lang/" + lang + ".yml")));
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(inputStreamReader);
        var currentYaml = YamlConfiguration.loadConfiguration(currentLangFile);
        if (currentLangFile.exists() && !paramsVersionCheck.getCurrentVersion(currentYaml).equals(Objects.requireNonNull(paramsVersionCheck.getNewVersion(yamlConfiguration)))) {
            log.info("Found new version of lang file, updating this now...");
            Path renameOldLang = new File(wgrpBukkitBase.getDataFolder(),
                    "lang/" + lang + "-old-" + paramsVersionCheck.getSimpleDateFormat() + ".yml").toPath();
            Files.move(currentLangFile.toPath(), renameOldLang, StandardCopyOption.REPLACE_EXISTING);
            wgrpBukkitBase.saveResource("lang/" + lang + ".yml", true);
        } else {
            log.info("No update is required for the lang file");
        }
    }

}
