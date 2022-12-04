package net.ritasister.wgrp.util;

import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.Message;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UtilConfig {

	private Config config;
	private File messages;

	public void initConfig(WorldGuardRegionProtect wgRegionProtect, WGRPBukkitPlugin wgrpBukkitPlugin) {
		config = new Config(wgRegionProtect, wgrpBukkitPlugin);
		loadMessages(wgRegionProtect);

		wgRegionProtect.getLogger().info(Component.text("&2All configs load successfully!"));
	}

	public void loadMessages(@NotNull WorldGuardRegionProtect wgRegionProtect) {
		Message.load(wgRegionProtect.getWGRPBukkitPlugin(), config.getLang(), wgRegionProtect.getLoadLibs().isPlaceholderAPIEnabled());
	}

	public Config getConfig() {
		return this.config;
	}

	public File getMessages() {
		return this.messages;
	}
}