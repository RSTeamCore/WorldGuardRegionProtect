package net.ritasister.wgrp.util;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.Message;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UtilConfig {

	private Config config;

	public void initConfig(WorldGuardRegionProtect wgRegionProtect) {
		config = new Config(wgRegionProtect, wgRegionProtect.getWgrpBukkitPlugin().getConfig());
		loadMessages(wgRegionProtect);

		wgRegionProtect.getRsApi().getLogger().info("&2All configs load successfully!");
	}

	private void loadMessages(@NotNull WorldGuardRegionProtect wgRegionProtect) {
		File messages = new File(wgRegionProtect.getWgrpBukkitPlugin().getDataFolder() + File.separator + "messages.yml");
		if(!messages.exists()) {
			try {
				messages.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		InputStream ddlStream = WGRPBukkitPlugin.class.getClassLoader().getResourceAsStream("messages.yml");
		try (FileOutputStream fos = new FileOutputStream(messages)) {
			byte[] buf = new byte[2048];
			int r;
			if (ddlStream != null) {
				while(-1 != (r = ddlStream.read(buf))) {
					fos.write(buf, 0, r);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Message.load(messages, wgRegionProtect.getLoadLibs().PlaceholderAPIEnabled);
	}

	public Config getConfig() {
		return this.config;
	}
}