package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class UtilConfig {

	private final WorldGuardRegionProtect wgRegionProtect;

	public String configVersion = "1.0";
	public List<String> regionProtect;
	public List<String> regionProtectAllow;
	public List<String> regionProtectOnlyBreakAllow;
	public List<String> interactType;
	public List<String> spyCommand;
	public List<String> cmdWe;
	public List<String> cmdWeC;
	public List<String> cmdWeP;
	public List<String> cmdWeS;
	public List<String> cmdWeU;
	public List<String> cmdWeCP;
	public List<String> cmdWeB;
	public boolean regionMessageProtect;
	public boolean regionMessageProtectWe;
	public boolean spyCommandNotifyConsole;
	public boolean spyCommandNotifyAdmin;
	public String spyCommandNotifyAdminPlaySound;
	public boolean spyCommandNotifyAdminPlaySoundEnable;
	public boolean databaseEnable;
	public String database;
	public String jdbcDriver;
	public String host;
	public String port;
	public String user;
	public String password;
	public String table;
	public boolean useSsl;

	//Pool settings
	public int maxPoolSize;
	public int maxLifetime;
	public int connectionTimeout;
	public int intervalReload;

	public String getConfigVersion() {
		return this.configVersion;
	}

	@SuppressWarnings("unchecked")
	public UtilConfig(WorldGuardRegionProtect wgRegionProtect) {
		this.wgRegionProtect = wgRegionProtect;
		try {
			this.configVersion = getConfigPatch().getString("config_version", configVersion);
			this.regionProtect = (List<String>) getConfigPatch().getList("region_protect", new ArrayList<String>());
			this.regionProtectAllow = (List<String>) getConfigPatch().getList("region_protect_allow", new ArrayList<String>());
			this.regionProtectOnlyBreakAllow = (List<String>) getConfigPatch().getList("region_protect_only_break_allow", new ArrayList<String>());
			this.interactType = (List<String>) getConfigPatch().getList("interact_type", new ArrayList<String>());

			this.cmdWe = (List<String>) getConfigPatch().getList("no_protect_cmd.command_we", new ArrayList<String>());
			this.cmdWeC = (List<String>) getConfigPatch().getList("no_protect_cmd.command_c", new ArrayList<String>());
			this.cmdWeP = (List<String>) getConfigPatch().getList("no_protect_cmd.command_p", new ArrayList<String>());
			this.cmdWeS = (List<String>) getConfigPatch().getList("no_protect_cmd.command_s", new ArrayList<String>());
			this.cmdWeU = (List<String>) getConfigPatch().getList("no_protect_cmd.command_u", new ArrayList<String>());
			this.cmdWeCP = (List<String>) getConfigPatch().getList("no_protect_cmd.command_cp", new ArrayList<String>());
			this.cmdWeB = (List<String>) getConfigPatch().getList("no_protect_cmd.command_b", new ArrayList<String>());
			this.spyCommand = (List<String>) getConfigPatch().getList("spy_command.command_list", new ArrayList<String>());

			this.regionMessageProtect = getConfigPatch().getBoolean("protect_message", true);
			this.regionMessageProtectWe = getConfigPatch().getBoolean("protect_we_message", true);
			this.spyCommandNotifyConsole = getConfigPatch().getBoolean("spy_command.notify.console", true);
			this.spyCommandNotifyAdmin = getConfigPatch().getBoolean("spy_command.notify.admin", true);
			this.spyCommandNotifyAdminPlaySoundEnable = getConfigPatch().getBoolean("spy_command.notify.admin.sound", true);
			this.spyCommandNotifyAdminPlaySound = getConfigPatch().getString("spy_command.notify.admin.sound.type", "BLOCK_ANVIL_PLACE");

			this.databaseEnable = getConfigPatch().getBoolean("dataSource.enable", false);
			this.database = getConfigPatch().getString("dataSource.database", "wgrp_core");
			this.jdbcDriver = getConfigPatch().getString("dataSource.jdbcdriver", "mariadb");
			this.host = getConfigPatch().getString("dataSource.host", "localhost");
			this.port = getConfigPatch().getString("dataSource.port", "3306");
			this.user = getConfigPatch().getString("dataSource.user", "root");
			this.password = getConfigPatch().getString("dataSource.password", "root");
			this.table = getConfigPatch().getString("dataSource.table", "wgrp_logs");
			this.useSsl = getConfigPatch().getBoolean("dataSource.useSsl", true);

			this.maxPoolSize = getConfigPatch().getInt("dataSource.maxPoolSize", 10);
			this.maxLifetime = getConfigPatch().getInt("dataSource.maxLifetime", 1800);
			this.connectionTimeout = getConfigPatch().getInt("dataSource.connectionTimeout", 5000);
			this.intervalReload = getConfigPatch().getInt("dataSource.intervalReload", 60);
		}catch(IllegalArgumentException e){
			wgRegionProtect.getRsApi().getLogger().warn("&cPlease update your config.");
		}
	}

	private ConfigurationSection getConfigPatch() {
		return wgRegionProtect.getUtilLoadConfig().config.getConfigurationSection("worldguard_protect_region.");
	}
}