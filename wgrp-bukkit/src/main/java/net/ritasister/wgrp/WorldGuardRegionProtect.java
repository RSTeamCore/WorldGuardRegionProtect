package net.ritasister.wgrp;

import net.ritasister.register.RegisterCommand;
import net.ritasister.register.RegisterListener;
import net.ritasister.rslibs.api.RSApi;
import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.rslibs.datasource.Storage;
import net.ritasister.rslibs.datasource.StorageDataBase;
import net.ritasister.rslibs.datasource.StorageDataSource;
import net.ritasister.rslibs.util.Metrics;
import net.ritasister.rslibs.util.UpdateChecker;
import net.ritasister.util.UtilLoadConfig;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class WorldGuardRegionProtect extends JavaPlugin {

    /** Instance of this class */
    private static WorldGuardRegionProtect instance;

    public WorldGuardRegionProtect() {}

    /**
     * Returns instance of this class
     *
     * @return instance of this class
     */
    public static WorldGuardRegionProtect getInstance() {
        return instance;
    }

    /**
     * Changes instance of this class to new value
     *
     * @param instance Instance to set variable to
     */
    public static void setInstance(WorldGuardRegionProtect instance) {
        WorldGuardRegionProtect.instance = instance;
    }

    private final PluginManager pluginManager = getServer().getPluginManager();
    private final String pluginVersion = getDescription().getVersion();
    public static StorageDataSource dbLogsSource;
    public static HashMap<UUID, StorageDataBase> dbLogs = new HashMap<>();

    //Configs
    public static UtilConfig utilConfig;
    public static UtilConfigMessage utilConfigMessage;

    @Override
    public void onEnable() {
        setInstance(this);
        this.checkStartUpVersionServer();
        this.loadMetrics();
        LoadLibs.loadWorldGuard();
        UtilLoadConfig.initConfig();
        RegisterCommand.RegisterCommands();
        RegisterListener.RegisterEvents(pluginManager);
        this.loadDataBase();
        this.notifyPreBuild();
        RSLogger.info("&2created by &8[&5RitaSister&8]");
        this.checkUpdate();
    }

    private void notifyPreBuild() {
        if(getInstance().pluginVersion.contains("pre")) {
            RSLogger.warn("This is a test build. This building may be unstable!");
        } else {
            RSLogger.info("This is the latest stable  building.");
        }
    }
    private void checkStartUpVersionServer() {
        if (!RSApi.isVersionSupported()) {
            RSLogger.err("This plugin version works only on 1.18+!");
            RSLogger.err("Please read this thread: https://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-18.81321/");
            RSLogger.err("The main post on spigotmc and please download the correct version.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
    private void checkUpdate() {
        new UpdateChecker(this, 81321).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                RSLogger.info("&6==============================================");
                RSLogger.info("&2Current version: &b<pl_ver>".replace("<pl_ver>", pluginVersion));
                RSLogger.info("&2This is the latest version of plugin.");
                RSLogger.info("&6==============================================");
            }else{
                RSLogger.info("&6==============================================");
                RSLogger.info("&eThere is a new version update available.");
                RSLogger.info("&cCurrent version: &4<pl_ver>".replace("<pl_ver>", pluginVersion));
                RSLogger.info("&3New version: &b<new_pl_ver>".replace("<new_pl_ver>", version));
                RSLogger.info("&eDownload new version here:");
                RSLogger.info("&ehttps://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-17.81321/");
                RSLogger.info("&6==============================================");
            }
        });
    }
    private void loadDataBase() {
        if(utilConfig.databaseEnable) {
            final long duration_time_start = System.currentTimeMillis();
            dbLogsSource = new Storage();
            dbLogs.clear();
            if(dbLogsSource == null) {
                RSLogger.err("[DataBase] Unable to connect to the database!");
            }
            if(dbLogsSource.load()) {
                RSLogger.info("[DataBase] The player base is loaded.");
                this.postEnable();
                RSLogger.info("[DataBase] Startup duration: {TIME} мс.".replace("{TIME}", String.valueOf(System.currentTimeMillis() - duration_time_start)));
            }
        }
    }
    private void postEnable() {
        Bukkit.getServer().getScheduler().cancelTasks(this);
        if (utilConfig.intervalReload > 0) {
            dbLogsSource.loadAsync();
            RSLogger.info("[DataBase] The base is loaded asynchronously.");
        }
    }
    private void loadMetrics() {
        int pluginId = 12975;
        new Metrics(this, pluginId);
    }
}