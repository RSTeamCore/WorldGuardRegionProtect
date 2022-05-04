package net.ritasister.util.config;

import net.ritasister.util.UtilLoadConfig;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public final class UtilConfig {
	public String configVersion;

	public List<String> regionProtect;
	public List<String> regionProtectAllow;
	public List<String> regionProtectOnlyBreakAllow;
	//public List<String> spawnEntityType;
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

	public final boolean databaseEnable;
	public final String database;
	public final String jdbcDriver;
	public final String host;
	public final String port;
	public final String user;
	public final String password;
	public final String table;
	public final boolean useSsl;

	//Pool settings
	public final int maxPoolSize;
	public final int maxLifetime;
	public final int connectionTimeout;
	public final int intervalReload;

	@SuppressWarnings("unchecked")
	public UtilConfig(WorldGuardRegionProtect instance) {
		this.configVersion = instance.getConfig().getString("config_version");

		this.regionProtect = (List<String>) getPatch().getList("region_protect", new ArrayList<String>());
		this.regionProtectAllow = (List<String>) getPatch().getList("region_protect_allow", new ArrayList<String>());
		this.regionProtectOnlyBreakAllow = (List<String>) getPatch().getList(".region_protect_only_break_allow", new ArrayList<String>());
		//this.spawnEntityType = (List<String>) getPatch().getList("spawn_entity_type", new ArrayList<String>());
		this.interactType = (List<String>) getPatch().getList("interact_type", new ArrayList<String>());

		this.cmdWe = (List<String>) getPatch().getList("no_protect_cmd.command_we", new ArrayList<String>());
		this.cmdWeC = (List<String>) getPatch().getList("no_protect_cmd.command_c", new ArrayList<String>());
		this.cmdWeP = (List<String>) getPatch().getList("no_protect_cmd.command_p", new ArrayList<String>());
		this.cmdWeS = (List<String>) getPatch().getList("no_protect_cmd.command_s", new ArrayList<String>());
		this.cmdWeU = (List<String>) getPatch().getList("no_protect_cmd.command_u", new ArrayList<String>());
		this.cmdWeCP = (List<String>) getPatch().getList("no_protect_cmd.command_cp", new ArrayList<String>());
		this.cmdWeB = (List<String>) getPatch().getList("no_protect_cmd.command_b", new ArrayList<String>());
		this.spyCommand = (List<String>) getPatch().getList("spy_command.command_list", new ArrayList<String>());

		this.regionMessageProtect = getPatch().getBoolean("protect_message");
		this.regionMessageProtectWe = getPatch().getBoolean("protect_we_message");
		this.spyCommandNotifyConsole = getPatch().getBoolean("spy_command.notify.console");
		this.spyCommandNotifyAdmin = getPatch().getBoolean("spy_command.notify.admin");
		this.spyCommandNotifyAdminPlaySoundEnable = getPatch().getBoolean("spy_command.notify.admin.sound");
		this.spyCommandNotifyAdminPlaySound = getPatch().getString("spy_command.notify.admin.sound.type");

		this.databaseEnable = getPatch().getBoolean("dataSource.enable");
		this.database = getPatch().getString("dataSource.database");
		this.jdbcDriver = getPatch().getString("dataSource.host");
		this.host = getPatch().getString("dataSource.port");
		this.port = getPatch().getString("dataSource.user");
		this.user = getPatch().getString("dataSource.password");
		this.password = getPatch().getString("dataSource.");
		this.table = getPatch().getString("dataSource.table");
		this.useSsl = getPatch().getBoolean("dataSource.useSsl");

		this.maxPoolSize = getPatch().getInt("dataSource.maxPoolSize");
		this.maxLifetime = getPatch().getInt("dataSource.maxLifetime");
		this.connectionTimeout = getPatch().getInt("dataSource.connectionTimeout");
		this.intervalReload = getPatch().getInt("dataSource.intervalReload");
	}
	private ConfigurationSection getPatch() {
		return UtilLoadConfig.config.getConfigurationSection("worldguard_protect_region.");
	}

	public String getConfigVersion() {
		return configVersion;
	}
}