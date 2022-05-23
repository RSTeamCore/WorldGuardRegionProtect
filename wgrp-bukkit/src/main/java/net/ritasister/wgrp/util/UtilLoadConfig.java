package net.ritasister.wgrp.util;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.UtilConfigMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UtilLoadConfig {

	private final WorldGuardRegionProtect wgRegionProtect;
	public FileConfiguration config, messages;

	private Config conf;

	public File configFile, messagesFile;

	public UtilLoadConfig(WorldGuardRegionProtect wgRegionProtect) {
		this.wgRegionProtect=wgRegionProtect;
	}

	public void initConfig(WorldGuardRegionProtect wgRegionProtect) {
		conf = new Config(wgRegionProtect, wgRegionProtect.getWgrpBukkitPlugin().getConfig());

		loadMSGConfig(wgRegionProtect, true);
		wgRegionProtect.utilConfigMessage = new UtilConfigMessage(wgRegionProtect);

		wgRegionProtect.getRsApi().getLogger().info("&2All configs load successfully!");
	}

	public Config getConfig() {
		return this.conf;
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