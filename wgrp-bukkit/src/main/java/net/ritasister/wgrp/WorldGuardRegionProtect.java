package net.ritasister.wgrp;

import net.ritasister.register.RegisterCommand;
import net.ritasister.register.RegisterListener;
import net.ritasister.rslibs.api.*;
import net.ritasister.rslibs.chat.api.ChatApi;
import net.ritasister.rslibs.datasource.Storage;
import net.ritasister.rslibs.util.Metrics;
import net.ritasister.rslibs.util.UpdateChecker;
import net.ritasister.util.UtilLoadConfig;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;
import net.ritasister.util.wg.wg7;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class WorldGuardRegionProtect extends JavaPlugin {

    /** Instance of the API */
    private static WorldGuardRegionProtect instance;

    /**
     * Instance setter for internal use by the plugin only.
     *
     * @param   instance
     *          API instance
     */
    public static void setInstance(WorldGuardRegionProtect instance) {
        WorldGuardRegionProtect.instance = instance;
    }

    /**
     * Returns API instance. If instance was not set by the plugin, throws
     * {@code IllegalStateException}. This is usually caused by shading the API
     * into own project, which is not allowed. Another option is calling the method
     * before plugin was able to load.
     *
     * @return  API instance
     * @throws  IllegalStateException
     *          If instance is {@code null}
     */
    public static WorldGuardRegionProtect getInstance() {
        if (instance == null)
            throw new IllegalStateException("API instance is null. This likely means you shaded WGRP's API into your project" +
                    " instead of only using it, which is not allowed.");
        return instance;
    }

    public ArrayList<UUID> spyLog = new ArrayList<>();
    public Functions functions;

    private final String pluginVersion = getDescription().getVersion();
    private final PluginManager pluginManager = getServer().getPluginManager();

    public wg7 wg7;

    //DataBase
    private RSStorage rsStorage;
    private ChatApi chatApi;
    private RSApi rsApi;
    private RSRegion rsRegion;
    public RSLogger rsLogger;

    //Configs
    public UtilConfig utilConfig;
    public UtilConfigMessage utilConfigMessage;
    private UtilLoadConfig utilLoadConfig;

    public LoadLibs loadLibs;

    public String name;

    @Override
    public void onEnable() {
        setInstance(this);
        this.rsApi = new RSApi(this);
        this.chatApi = new ChatApi();
        this.rsLogger = new RSLogger(this.chatApi);
        this.checkStartUpVersionServer();
        this.loadMetrics();
        this.loadAnotherClassAndMethods();
        //this.loadDataBase();
        this.notifyPreBuild();
        this.checkUpdate();
    }

    /**
     * Call all classes file to start.
     */
    private void loadAnotherClassAndMethods() {
        this.loadLibs = new LoadLibs();
        this.loadLibs.loadWorldGuard();
        //this.functions = new Functions(this);
        this.rsRegion = new RSRegion();
        this.wg7 = new wg7(this);
        this.utilLoadConfig = new UtilLoadConfig(this);
        this.utilLoadConfig.initConfig();
        //Implements
        RegisterListener registerListener = new RegisterListener();
        registerListener.registerListener(this.pluginManager);
        RegisterCommand registerCommand = new RegisterCommand();
        registerCommand.registerCommand();
        this.rsStorage = new RSStorage();
    }

    private void checkStartUpVersionServer() {
        if (!this.getRsApi().isVersionSupported()) {
            getRsApi().getLogger().error("This plugin version works only on 1.18+!");
            getRsApi().getLogger().error("Please read this thread: https://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-18.81321/");
            getRsApi().getLogger().error("The main post on spigotmc and please download the correct version.");
            WorldGuardRegionProtect.getInstance().getServer().getPluginManager().disablePlugin(this);
            getLogger().info("");
        }
    }

    private void notifyPreBuild() {
        if(this.pluginVersion.contains("pre")) {
            getRsApi().getLogger().warn("This is a test build. This building may be unstable!");
        } else {
            getRsApi().getLogger().info("This is the latest stable building.");
        }
    }

    private void checkUpdate() {
        new UpdateChecker(this, 81321).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getRsApi().getLogger().info("&6==============================================");
                getRsApi().getLogger().info("&2Current version: &b<pl_ver>".replace("<pl_ver>", pluginVersion));
                getRsApi().getLogger().info("&2This is the latest version of plugin.");
                getRsApi().getLogger().info("&6==============================================");
            }else{
                getRsApi().getLogger().info("&6==============================================");
                getRsApi().getLogger().info("&eThere is a new version update available.");
                getRsApi().getLogger().info("&cCurrent version: &4<pl_ver>".replace("<pl_ver>", pluginVersion));
                getRsApi().getLogger().info("&3New version: &b<new_pl_ver>".replace("<new_pl_ver>", version));
                getRsApi().getLogger().info("&eDownload new version here:");
                getRsApi().getLogger().info("&ehttps://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-17.81321/");
                getRsApi().getLogger().info("&6==============================================");
            }
        });
    }

    private void loadDataBase() {
        if(this.utilConfig.databaseEnable) {
            final long duration_time_start = System.currentTimeMillis();
            this.rsStorage.dbLogsSource = new Storage(this);
            this.rsStorage.dbLogs.clear();
            if (this.rsStorage.dbLogsSource.load()) {
                getRsApi().getLogger().info("[DataBase] The player base is loaded.");
                this.postEnable();
                getRsApi().getLogger().info("[DataBase] Startup duration: {TIME} мс.".replace("{TIME}", String.valueOf(System.currentTimeMillis() - duration_time_start)));
            }
        }
    }

    private void postEnable() {
        getServer().getScheduler().cancelTasks(this);
        if (WorldGuardRegionProtect.getInstance().utilConfig.intervalReload > 0) {
            rsStorage.dbLogsSource.loadAsync();
            getRsApi().getLogger().info("[DataBase] The base is loaded asynchronously.");
        }
    }

    private void loadMetrics() {
        int pluginId = 12975;
        new Metrics(this, pluginId);
    }

    @NotNull
    public RSApi getRsApi() {
        return this.rsApi;
    }

    @NotNull
    public RSRegion getRsRegion() {
        return this.rsRegion;
    }

    @NotNull
    public RSStorage getRsStorage() {
        return this.rsStorage;
    }

    @NotNull
    public ChatApi getChatApi() {
        return this.chatApi;
    }

    @NotNull
    public UtilLoadConfig getUtilLoadConfig() {
        return utilLoadConfig;
    }

    @NotNull
    public UtilConfigMessage getUtilConfigMessage() {
        return utilConfigMessage;
    }

    @NotNull
    public UtilConfig getUtilConfig() {
        return  utilConfig;
    }

    @NotNull
    public wg7 getWG7() {
        return wg7;
    }

}