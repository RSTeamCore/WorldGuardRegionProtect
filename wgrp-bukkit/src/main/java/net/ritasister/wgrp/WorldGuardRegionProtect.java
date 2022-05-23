package net.ritasister.wgrp;

import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import net.ritasister.wgrp.rslibs.api.*;
import net.ritasister.wgrp.rslibs.util.wg.Iwg;
import net.ritasister.wgrp.util.UtilConfig;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldGuardRegionProtect {

    private final WGRPBukkitPlugin wgrpBukkitPlugin;

    private Iwg Iwg;

    //DataBase
    private RSStorage rsStorage;
    private ChatApi chatApi;
    private RSApi rsApi;
    private RSRegion rsRegion;
    private RSLogger rsLogger;

    //Configs
    private UtilConfig utilConfig;

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
        this.getWgrpBukkitPlugin().loadDataBase();
        this.getWgrpBukkitPlugin().notifyPreBuild();
        this.getWgrpBukkitPlugin().checkUpdate();
    }

    private void loadAnotherClassAndMethods() {
        //Libs of WorldGuard.
        this.loadLibs = new LoadLibs(this);
        this.getLoadLibs().loadPlaceholderAPI();
        this.getLoadLibs().loadWorldGuard();

        //API for Regions.
        this.rsRegion = new RSRegion();

        //Configs.
        this.utilConfig = new UtilConfig();
        this.getUtilConfig().initConfig(this);

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
            getRsApi().getLogger().error("Please read this thread: https://www.spigotmc.org/resources/81321/");
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
    public UtilConfig getUtilConfig() {
        return utilConfig;
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

    public List<String> getPluginAuthors() {
        return this.wgrpBukkitPlugin.getDescription().getAuthors();
    }

    @NotNull
    public Iwg getIwg() {
        return this.Iwg;
    }
}