package net.ritasister.wgrp;

import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import net.ritasister.wgrp.rslibs.api.*;
import net.ritasister.wgrp.rslibs.util.wg.Iwg;
import net.ritasister.wgrp.util.UtilLoadConfig;
import net.ritasister.wgrp.util.config.UtilConfig;
import net.ritasister.wgrp.util.config.UtilConfigMessage;
import net.ritasister.wgrp.util.wg.wg7;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class WorldGuardRegionProtect {

    private final WGRPBukkitPlugin wgrpBukkitPlugin;

    //public Functions functions;

    private wg7 wg7;

    private Iwg Iwg;

    //DataBase
    private RSStorage rsStorage;
    private ChatApi chatApi;
    private RSApi rsApi;
    private RSRegion rsRegion;
    private RSLogger rsLogger;

    //Configs
    public UtilConfig utilConfig;
    public UtilConfigMessage utilConfigMessage;
    public UtilLoadConfig utilLoadConfig;

    //Parameters for to intercept commands from WE or FAWE.
    private CommandWE commandWE;

    //Load depends on WorldGuard
    private LoadLibs loadLibs;

    //HashMap
    public ArrayList<UUID> spyLog = new ArrayList<>();

    public WorldGuardRegionProtect(WGRPBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    public void load() {
        this.rsApi = new RSApi(this);
        this.chatApi = new ChatApi();
        this.rsLogger = new RSLogger(this.chatApi);
        this.checkStartUpVersionServer();
        this.getWgrpBukkitPlugin().loadMetrics();
        this.loadAnotherClassAndMethods();
        //this.loadDataBase();
        this.getWgrpBukkitPlugin().notifyPreBuild();
        this.getWgrpBukkitPlugin().checkUpdate();
    }

    private void loadAnotherClassAndMethods() {
        //Libs of WorldGuard.
        this.loadLibs = new LoadLibs(this);
        this.getLoadLibs().loadWorldGuard();
        //this.functions = new Functions(this);

        //API for Regions.
        this.rsRegion = new RSRegion();
        this.wg7 = new wg7(this);

        //Configs.
        this.utilLoadConfig = new UtilLoadConfig(this);
        this.getUtilLoadConfig().initConfig(this);

        //Parameters for to intercept commands from WE or FAWE.
        this.commandWE = new CommandWE(this);
        this.Iwg=this.getCommandWE().setUpWorldGuardVersionSeven();

        //Commands and listeners.
        this.loadCommandsAndListener();

        //Fields for database.
        this.rsStorage = new RSStorage();
    }

    private void loadCommandsAndListener() {
        ListenerHandler registerListener = new ListenerHandler(this);
        registerListener.listenerHandler(this.getPluginManager());
        CommandHandler commandHandler = new CommandHandler(this);
        commandHandler.commandHandler();
    }

    private void checkStartUpVersionServer() {
        if (!this.getRsApi().isVersionSupported()) {
            getRsApi().getLogger().error("This plugin version works only on 1.18+!");
            getRsApi().getLogger().error("Please read this thread: https://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-18.81321/");
            getRsApi().getLogger().error("The main post on spigotmc and please download the correct version.");
            getWgrpBukkitPlugin().getServer().getPluginManager().disablePlugin(wgrpBukkitPlugin);
        }
    }

    @NotNull
    public WGRPBukkitPlugin getWgrpBukkitPlugin() {
        return this.wgrpBukkitPlugin;
    }

    @NotNull
    public RSApi getRsApi() {
        return this.rsApi;
    }

    @NotNull
    public RSLogger getRsLogger() {
        return this.rsLogger;
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
    public LoadLibs getLoadLibs() {
        return this.loadLibs;
    }

    @NotNull
    public CommandWE getCommandWE() {
        return this.commandWE;
    }

    @NotNull
    public PluginManager getPluginManager() {
        return this.wgrpBukkitPlugin.getServer().getPluginManager();
    }

    @NotNull
    public String getPluginVersion() {
        return this.wgrpBukkitPlugin.getDescription().getVersion();
    }

    @NotNull
    public wg7 getWG7() {
        return this.wg7;
    }

    @NotNull
    public Iwg getIwg() {
        return this.Iwg;
    }
}