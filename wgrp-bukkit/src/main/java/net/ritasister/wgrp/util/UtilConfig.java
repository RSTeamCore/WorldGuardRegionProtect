package net.ritasister.wgrp.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.Container;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@RequiredArgsConstructor
public class UtilConfig {

	@Getter private Config config;
	@Getter private Container messages;

	public void initConfig(WorldGuardRegionProtect wgRegionProtect, WGRPBukkitPlugin wgrpBukkitPlugin) {
		config = new Config(wgRegionProtect, wgrpBukkitPlugin);
		messages = loadMessages(wgrpBukkitPlugin);
		wgRegionProtect.getLogger().info(Component.text("&2All configs load successfully!"));
	}

	public Container loadMessages(@NotNull WGRPBukkitPlugin wgrpBukkitPlugin) {
		String lang = config.getLang();
		File file = new File(wgrpBukkitPlugin.getDataFolder(), lang + ".yml");
		if(!file.exists()) {
			wgrpBukkitPlugin.saveResource(lang + ".yml", false);
		}
		return new Container(YamlConfiguration.loadConfiguration(file));
	}
}