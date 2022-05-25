package net.ritasister.wgrp.util;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.Message;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UtilConfig {

	private Config config;
	private File messages;

	public void initConfig(WorldGuardRegionProtect wgRegionProtect) {
		config = new Config(wgRegionProtect, wgRegionProtect.getWgrpBukkitPlugin().getConfig());
		loadMessages(wgRegionProtect);

		wgRegionProtect.getRsApi().getLogger().info("&2All configs load successfully!");
	}

	public void loadMessages(@NotNull WorldGuardRegionProtect wgRegionProtect) {
		Message.load(wgRegionProtect.getWgrpBukkitPlugin(), config.getLang(), wgRegionProtect.getLoadLibs().PlaceholderAPIEnabled);
	}

	public Config getConfig() {
		return this.config;
	}

	public File getMessages() {
		return this.messages;
	}
}