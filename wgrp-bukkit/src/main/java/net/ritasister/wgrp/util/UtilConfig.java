package net.ritasister.wgrp.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.Container;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;

@RequiredArgsConstructor
public class UtilConfig {

	@Getter private Config config;
	@Getter private Container messages;

	@SneakyThrows
	public void initConfig(WorldGuardRegionProtect wgRegionProtect, WGRPBukkitPlugin wgrpBukkitPlugin) {
		config = new Config(wgRegionProtect, wgrpBukkitPlugin);
		messages = loadMessages(wgrpBukkitPlugin);
		checkLangVersion(wgrpBukkitPlugin);
		wgRegionProtect.getLogger().info("All configs load successfully!");
	}

	@SneakyThrows
	public Container loadMessages(@NotNull WGRPBukkitPlugin wgrpBukkitPlugin) {
		String lang = config.getLang();
		File file = new File(wgrpBukkitPlugin.getDataFolder(), "lang/" + lang + ".yml");
		if (!file.exists()) {
			wgrpBukkitPlugin.saveResource("lang/" + lang + ".yml", false);
		}
		return new Container(YamlConfiguration.loadConfiguration(file));
	}

	public void checkLangVersion(@NotNull WGRPBukkitPlugin wgrpBukkitPlugin) throws IOException {
		String lang = config.getLang();
		File currentLangFile = new File(wgrpBukkitPlugin.getDataFolder(), "lang/" + lang + ".yml");
		InputStream inputStream = wgrpBukkitPlugin.getResource("lang/" + lang + ".yml");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
		String content;
		String newVersion = null;
		String currentVersion = getMessages().get("langTitle.version").toString();
		while ((content = bufferedReader.readLine()) != null) {
			if(content.contains("version")) {
				newVersion = content.replace(" version: ", "").replace("version: ", "").replaceAll("\"", "");
				break;
			}
		}
		if (currentLangFile.exists() && (currentVersion).equals(newVersion)) {
			Bukkit.getConsoleSender().sendMessage("[WGRP] Found new version of lang file, we are updated this now...");
			wgrpBukkitPlugin.saveResource("lang/" + lang + ".yml", true);
		} else {
			Bukkit.getConsoleSender().sendMessage("[WGRP] No update is required for the lang file");
		}
	}
}