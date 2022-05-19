package net.ritasister.wgrp.util;

import net.ritasister.wgrp.util.config.UtilConfig;
import net.ritasister.wgrp.util.config.UtilConfigMessage;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UtilLoadConfig {

	private final WorldGuardRegionProtect wgRegionProtect;
	public FileConfiguration config, messages;
	public File configFile, messagesFile;

	public UtilLoadConfig(WorldGuardRegionProtect wgRegionProtect) {
		this.wgRegionProtect=wgRegionProtect;
	}

	public void initConfig(WorldGuardRegionProtect wgRegionProtect) {
		loadConfig(wgRegionProtect, true);
		wgRegionProtect.utilConfig = new UtilConfig(wgRegionProtect);

		loadMSGConfig(wgRegionProtect, true);
		wgRegionProtect.utilConfigMessage = new UtilConfigMessage(wgRegionProtect);

		wgRegionProtect.getRsApi().getLogger().info("&2All configs load successfully!");
	}

	private void loadConfig(@NotNull WorldGuardRegionProtect worldGuardRegionProtect, boolean copy) {
		configFile = new File(wgRegionProtect.getWgrpBukkitPlugin().getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		if (!configFile.exists()) {
			if (copy) {
				wgRegionProtect.getWgrpBukkitPlugin().saveResource("config.yml", false);
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
		messagesFile = new File(wgRegionProtect.getWgrpBukkitPlugin().getDataFolder(), "messages.yml");
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
				wgRegionProtect.getWgrpBukkitPlugin().saveResource("messages.yml", false);
				//RSLogger.updateConfigMsgSuccess(configFile);
			} else {
				try {
					messagesFile.createNewFile();
				} catch (Exception ex) {
					worldGuardRegionProtect.getRsApi().getLogger().error(messagesFile + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}try {
			messages = YamlConfiguration.loadConfiguration(messagesFile);
		} catch (NullPointerException e) {
			worldGuardRegionProtect.getRsApi().getLogger().error(messagesFile + e.getMessage());
			e.printStackTrace();
		}
	}
}