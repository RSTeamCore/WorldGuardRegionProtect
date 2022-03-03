package net.ritasister.wgrp;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.ritasister.register.RegisterCommand;
import net.ritasister.register.RegisterListener;

import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.rslibs.utils.Metrics;
import net.ritasister.rslibs.utils.UpdateChecker;
import net.ritasister.rslibs.datasource.Storage;
import net.ritasister.rslibs.datasource.StorageDataBase;
import net.ritasister.rslibs.datasource.StorageDataSource;

import net.ritasister.util.UtilCommandList;
import net.ritasister.util.UtilConfigManager;
import net.ritasister.util.UtilLoadConfig;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;
import net.ritasister.util.config.UtilDataStorage;

public class WorldGuardRegionProtect extends JavaPlugin {

	public static WorldGuardRegionProtect instance;
	public static UtilConfig utilConfig;
	public static UtilConfigMessage utilConfigMessage;
	public static UtilCommandList utilCommandList;
	public static UtilDataStorage utilDataStorage;

	public final UtilLoadConfig utilLoadConfig = new UtilLoadConfig(instance);
	private final PluginManager pluginManager = getServer().getPluginManager();
	private final String pluginVersion = getDescription().getVersion();

	public static StorageDataSource dbLogsSource;
	public static HashMap<UUID, StorageDataBase> dbLogs = new HashMap<>();

	public WorldGuardRegionProtect() {WorldGuardRegionProtect.instance = this;}

	@Override
	public void onEnable() {
		this.checkStartUpVersionServer();
		this.checkJavaAndServerVersion();
		this.loadMetrics();
		LoadLibs.loadWorldGuard();
		UtilConfigManager.loadConfig();
		RegisterCommand.RegisterCommands(utilCommandList);
		RegisterListener.RegisterEvents(pluginManager);
		Bukkit.getConsoleSender().sendMessage("current version file config: " + utilConfig.configVersion);
		//Maybe I do database in the next build's for logging.
		//this.loadDataBase();
		this.sendMessageIfTestBuild();
		RSLogger.info("&2created by &8[&5RitaSister&8]");
		this.checkUpdate();
	}

	private void sendMessageIfTestBuild() {
		if(instance.pluginVersion.contains("pre")) {
			Bukkit.getConsoleSender().sendMessage("This is a test build. This build maybe will unstable!");
		}else{
			Bukkit.getConsoleSender().sendMessage("This is a stable build.");
		}
	}

	private void checkStartUpVersionServer() {
		if(!Bukkit.getVersion().contains("1.16.5")
				&& !Bukkit.getVersion().contains("1.17")
				&& !Bukkit.getVersion().contains("1.18")) {
			RSLogger.err("This plugin version work only on 1.16.5+!");
			RSLogger.err("Please read: https://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-18.81321/");
			RSLogger.err("The main post on spigotmc and download correct version.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	private void checkUpdate() {
		new UpdateChecker(this, 81321).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
            	RSLogger.info("&6==============================================");
                RSLogger.info("&2Current version: &b<pl_ver>".replace("<pl_ver>", pluginVersion));
                RSLogger.info("&2This is latest version plugin.".replace("<pl_ver>", pluginVersion));
                RSLogger.info("&6==============================================");
            }else{
            	RSLogger.info("&6==============================================");
                RSLogger.info("&eThere is a new version update available.");
                RSLogger.info("&cCurrent version: &4<pl_ver>".replace("<pl_ver>", pluginVersion));
                RSLogger.info("&3New version: &b<new_pl_ver>".replace("<new_pl_ver>", version));
                RSLogger.info("&ePlease download new version here:");
                RSLogger.info("&ehttps://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-17.81321/");
                RSLogger.info("&6==============================================");
            }
        });
	}
	private void checkJavaAndServerVersion() {
		final String javaVersion = System.getProperty("java.version");
		final int dotIndex = javaVersion.indexOf('.');
		final int endIndex = dotIndex == -1 ? javaVersion.length() : dotIndex;
		final String version = javaVersion.substring(0, endIndex);
		final int javaVersionNum;
        try{
            	javaVersionNum = Integer.parseInt(version);
        }catch(final NumberFormatException e){
            	RSLogger.warn("Failed to determine Java version; Could not parse {}".replace("{}", version) + e);
            	RSLogger.warn(javaVersion);
            	return;
        }String serverVersion;
		try{
            	serverVersion = instance.getServer().getClass().getPackage().getName().split("\\.")[3];
        }catch(ArrayIndexOutOfBoundsException whatVersionAreYouUsingException){
            	return;
        }
        RSLogger.info("&6You are running is &ejava &6version: &e<javaVersion>".replace("<javaVersion>", String.valueOf(javaVersionNum)));
		RSLogger.info("&6Your &eserver &6is running version: &e<serverVersion>".replace("<serverVersion>", String.valueOf(serverVersion)));
	}
	private void loadDataBase() {
		if(utilDataStorage.databaseEnable) {
			final long duration_time_start = System.currentTimeMillis();
			dbLogsSource = new Storage();
			dbLogs.clear();
			if(dbLogsSource == null) {
				RSLogger.err("[DataBase] Не удаётся соединиться с базой данных!");
			}
			if(dbLogsSource.load()) {
				RSLogger.info("[DataBase] База игроков загружена.");
				this.postEnable();
				RSLogger.info("[DataBase] Продолжительность запуска: {TIME} мс.".replace("{TIME}", String.valueOf(System.currentTimeMillis() - duration_time_start)));
			}
		}
	}
	private void postEnable() {
		Bukkit.getServer().getScheduler().cancelTasks(this);
		if (utilDataStorage.intervalReload > 0) {
			dbLogsSource.loadAsync();
			RSLogger.info("[DataBase] База загружена асинхронно.");
		}
	}
	private void loadMetrics() {
		int pluginId = 12975;
		new Metrics(this, pluginId);
	}
}
