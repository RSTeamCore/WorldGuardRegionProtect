package net.ritasister.wgrp.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.Config;
import net.rsteamcore.config.Container;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
public class UtilConfig {

	@Getter private Config config;
	@Getter private Container messages;

	public void initConfig(WorldGuardRegionProtect wgRegionProtect, WGRPBukkitPlugin wgrpBukkitPlugin) {
		config = new Config(wgRegionProtect, wgrpBukkitPlugin);
		messages = loadMessages(wgrpBukkitPlugin);
		checkLangVersion(wgrpBukkitPlugin);
		wgRegionProtect.getLogger().info("All configs load successfully!");
	}

	public Container loadMessages(@NotNull WGRPBukkitPlugin wgrpBukkitPlugin) {
		String lang = config.getLang();
		File file = new File(wgrpBukkitPlugin.getDataFolder(), "lang/" + lang + ".yml");
		if (!file.exists()) {
			wgrpBukkitPlugin.saveResource("lang/" + lang + ".yml", false);
		}
		return new Container(YamlConfiguration.loadConfiguration(file));
	}


    //TODO: Need tested
	@SneakyThrows
	public void checkLangVersion(@NotNull WGRPBukkitPlugin wgrpBukkitPlugin) {
		String lang = config.getLang();
		File currentLangFile = new File(wgrpBukkitPlugin.getDataFolder(), "lang/" + lang + ".yml");
		InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(wgrpBukkitPlugin.getResource("lang/" + lang + ".yml")));
		YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(inputStreamReader);
		var currentYaml = YamlConfiguration.loadConfiguration(currentLangFile);
		if (currentLangFile.exists() && !getCurrentVersion(currentYaml).equals(Objects.requireNonNull(getNewVersion(yamlConfiguration)))) {
			Bukkit.getConsoleSender().sendMessage("[WGRP] Found new version of lang file, we are updated this now...");
			Path renameOldLang = new File(wgrpBukkitPlugin.getDataFolder(), "lang/" + lang + "-old-" + getSimpleDateFormat() + ".yml").toPath();
			Files.move(currentLangFile.toPath(),  renameOldLang, StandardCopyOption.REPLACE_EXISTING);
			wgrpBukkitPlugin.saveResource("lang/" + lang + ".yml", true);
		} else {
			Bukkit.getConsoleSender().sendMessage("[WGRP] No update is required for the lang file");
		}
	}

    private @NotNull String getSimpleDateFormat() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss");
        return simpleDateFormat.format(date);
    }

    private @NotNull String getCurrentVersion(final @NotNull YamlConfiguration currentYaml) {
        return Objects.requireNonNull(currentYaml.getString("langTitle.version"))
                .replaceAll("\"", "")
                .replaceAll("'", "");
    }

    private @NotNull String getNewVersion(final @NotNull YamlConfiguration yamlConfiguration) {
        return Objects.requireNonNull(yamlConfiguration.getString("langTitle.version"))
                .replaceAll("\"", "")
                .replaceAll("'", "");
    }
}
