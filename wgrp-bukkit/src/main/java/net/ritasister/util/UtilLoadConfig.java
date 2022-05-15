package net.ritasister.util;

import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class UtilLoadConfig {

	public static File configf, messagesf;
	public static FileConfiguration config, messages;
	private static WorldGuardRegionProtect worldGuardRegionProtect;
	private static UtilConfigMessage utilConfigMessage;

	public UtilLoadConfig(WorldGuardRegionProtect worldGuardRegionProtect, UtilConfig utilConfig, UtilConfigMessage utilConfigMessage) {
		UtilLoadConfig.worldGuardRegionProtect=worldGuardRegionProtect;
		UtilLoadConfig.utilConfigMessage=utilConfigMessage;
	}

	public static void initConfig() {
		LoadConfig(WorldGuardRegionProtect.getInstance(), true);
		WorldGuardRegionProtect.utilConfig = new UtilConfig();

		LoadMSGConfig(WorldGuardRegionProtect.getInstance(), true);
		WorldGuardRegionProtect.utilConfigMessage = new UtilConfigMessage();

		RSLogger.info("&2All configs load successfully!");
	}

	public static void LoadConfig(WorldGuardRegionProtect worldGuardRegionProtect, boolean copy) {
		configf = new File(worldGuardRegionProtect.getDataFolder(), "config.yml");
		if (!configf.exists()) {
			if (copy) {
				worldGuardRegionProtect.saveResource("config.yml", false);
				RSLogger.LoadConfigMsgSuccess(configf);
			} else {
				try {
					configf.createNewFile();
				} catch (Exception ex) {
					RSLogger.err(configf + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
		try {
			config = YamlConfiguration.loadConfiguration(configf);
		} catch (NullPointerException e) {
			RSLogger.err(configf + e.getMessage());
			e.printStackTrace();
		}
		RSLogger.LoadConfigMsgSuccess(messagesf);
	}

	public static void LoadMSGConfig(WorldGuardRegionProtect worldGuardRegionProtect, boolean copy) {
		messagesf = new File(worldGuardRegionProtect.getDataFolder(), "messages.yml");
		if (!messagesf.exists()) {
			if (copy) {
				worldGuardRegionProtect.saveResource("messages.yml", false);
				RSLogger.LoadConfigMsgSuccess(messagesf);
			}else{
				try {
					messagesf.createNewFile();
				}catch(Exception ex){
					RSLogger.err(messagesf+ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
		try {
			messages = YamlConfiguration.loadConfiguration(messagesf);
		}catch(NullPointerException e){
			RSLogger.err(messagesf+e.getMessage());
			e.printStackTrace();
		}
		RSLogger.LoadConfigMsgSuccess(messagesf);
	}

	public static WorldGuardRegionProtect getWorldGuardRegionProtect() {
		return worldGuardRegionProtect;
	}
}