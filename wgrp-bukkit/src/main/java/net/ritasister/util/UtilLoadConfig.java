package net.ritasister.util;

import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class UtilLoadConfig {

	private final WorldGuardRegionProtect worldGuardRegionProtect;
	public FileConfiguration config, messages;
	public File configFile, messagesFile;

	public UtilLoadConfig(WorldGuardRegionProtect worldGuardRegionProtect) {
		this.worldGuardRegionProtect=worldGuardRegionProtect;
	}

	public void initConfig() {
		loadConfig(WorldGuardRegionProtect.getInstance(), true);
		WorldGuardRegionProtect.getInstance().utilConfig = new UtilConfig();

		loadMSGConfig(WorldGuardRegionProtect.getInstance(), true);
		WorldGuardRegionProtect.getInstance().utilConfigMessage = new UtilConfigMessage();

		worldGuardRegionProtect.getRsApi().getLogger().info("&2All configs load successfully!");
	}

	private void loadConfig(@NotNull WorldGuardRegionProtect worldGuardRegionProtect, boolean copy) {
		configFile = new File(worldGuardRegionProtect.getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		if (!configFile.exists()) {
			if (copy) {
				worldGuardRegionProtect.saveResource("config.yml", false);
				//RSLogger.updateConfigMsgSuccess(configFile);
			} else {
				try {
					configFile.createNewFile();
				} catch (Exception ex) {
					worldGuardRegionProtect.getRsApi().getLogger().error(configFile + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
		/*if (configFile.exists() && !worldGuardRegionProtect.utilConfig.configVersion.equals("1.0")) {
			try {
				Files.move(configFile.toPath(), new File(configFile.getParentFile(), "config-old-" + worldGuardRegionProtect.getRsApi().getPathTime() + ".yml").toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			worldGuardRegionProtect.reloadConfig();
		}*/
			try {
			config = YamlConfiguration.loadConfiguration(configFile);
		} catch (NullPointerException e) {
			worldGuardRegionProtect.getRsApi().getLogger().error(configFile + e.getMessage());
			e.printStackTrace();
		}
		worldGuardRegionProtect.getRsApi().getLogger().loadConfigMsgSuccess(configFile);
	}

	private void loadMSGConfig(@NotNull WorldGuardRegionProtect worldGuardRegionProtect, boolean copy) {
		messagesFile = new File(worldGuardRegionProtect.getDataFolder(), "messages.yml");
		/*if (messagesFile.exists() && worldGuardRegionProtect.utilConfigMessage.configMsgReloaded == null
				|| !worldGuardRegionProtect.utilConfigMessage.configMsgReloaded.equals("1.0")) {
			try {
				Files.move(messagesFile.toPath(), new File(messagesFile.getParentFile(), "config-old-" + worldGuardRegionProtect.getRsApi().getPathTime() + ".yml").toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			worldGuardRegionProtect.reloadConfig();
		}*/
		if (!messagesFile.exists()) {
			if (copy) {
				worldGuardRegionProtect.saveResource("messages.yml", false);
				//RSLogger.updateConfigMsgSuccess(configFile);
			} else {
				try {
					messagesFile.createNewFile();
				} catch (Exception ex) {
					worldGuardRegionProtect.getRsApi().getLogger().error(messagesFile + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
		try {
			messages = YamlConfiguration.loadConfiguration(messagesFile);
		} catch (NullPointerException e) {
			worldGuardRegionProtect.getRsApi().getLogger().error(messagesFile + e.getMessage());
			e.printStackTrace();
		}
	}
}