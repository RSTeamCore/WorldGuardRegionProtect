package net.ritasister.wgrp.util.config.loader;

import lombok.SneakyThrows;
import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.abstracts.AbstractInitMessage;
import net.rsteamcore.config.Container;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class MessageLoader extends AbstractInitMessage {

    public Container initMessages(final @NotNull WGRPBukkitPlugin wgrpBukkitPlugin, final @NotNull Config config) {
        String lang = config.getLang();
        File file = new File(wgrpBukkitPlugin.getDataFolder(), "lang/" + lang + ".yml");
        if (!file.exists()) {
            wgrpBukkitPlugin.saveResource("lang/" + lang + ".yml", false);
        }
        return new Container(YamlConfiguration.loadConfiguration(file));
    }

    @Override
    @SneakyThrows
    public void checkVersionLang(final @NotNull WGRPBukkitPlugin wgrpBukkitPlugin, final @NotNull Config config) {
        String lang = config.getLang();
        File currentLangFile = new File(wgrpBukkitPlugin.getDataFolder(), "lang/" + lang + ".yml");
        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(wgrpBukkitPlugin.getResource("lang/" + lang + ".yml")));
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(inputStreamReader);
        var currentYaml = YamlConfiguration.loadConfiguration(currentLangFile);
        if (currentLangFile.exists() && !getCurrentVersion(currentYaml).equals(Objects.requireNonNull(getNewVersion(yamlConfiguration)))) {
            WGRPContainer.getLogger().info("Found new version of lang file, updating this now...");
            Path renameOldLang = new File(wgrpBukkitPlugin.getDataFolder(), "lang/" + lang + "-old-" + getSimpleDateFormat() + ".yml").toPath();
            Files.move(currentLangFile.toPath(), renameOldLang, StandardCopyOption.REPLACE_EXISTING);
            wgrpBukkitPlugin.saveResource("lang/" + lang + ".yml", true);
        } else {
            WGRPContainer.getLogger().info("No update is required for the lang file");
        }
    }

}
