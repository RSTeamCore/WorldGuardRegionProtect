package net.ritasister.wgrp;

import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.rslibs.chat.api.ChatApi;
import net.ritasister.wgrp.register.RegisterCommand;
import net.ritasister.wgrp.register.RegisterListener;
import net.ritasister.wgrp.rslibs.api.RSApi;
import net.ritasister.wgrp.rslibs.api.RSRegion;
import net.ritasister.wgrp.rslibs.api.RSStorage;
import net.ritasister.wgrp.util.UtilLoadConfig;
import net.ritasister.wgrp.util.config.UtilConfig;
import net.ritasister.wgrp.util.config.UtilConfigMessage;
import net.ritasister.wgrp.util.wg.wg7;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class WorldGuardRegionProtect {

    private WGRPBukkitPlugin wgrpBukkitPlugin;

    public ArrayList<UUID> spyLog = new ArrayList<>();
    //public Functions functions;

    public wg7 wg7;

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

    private LoadLibs loadLibs;

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

    /**
     * Call all classes file to start.
     */
    private void loadAnotherClassAndMethods() {
        this.loadLibs = new LoadLibs(this);
        this.getLoadLibs().loadWorldGuard();
        //this.functions = new Functions(this);
        this.rsRegion = new RSRegion();
        this.wg7 = new wg7(this);
        this.utilLoadConfig = new UtilLoadConfig(this);
        this.getUtilLoadConfig().initConfig(this);
        //Implements
        RegisterListener registerListener = new RegisterListener(this);
        registerListener.registerListener(this.getPluginManager());
        RegisterCommand registerCommand = new RegisterCommand(this);
        registerCommand.registerCommand();
        this.rsStorage = new RSStorage();
    }

    private void checkStartUpVersionServer() {
        if (!this.getRsApi().isVersionSupported()) {
            getRsApi().getLogger().error("This plugin version works only on 1.18+!");
            getRsApi().getLogger().error("Please read this thread: https://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-18.81321/");
            getRsApi().getLogger().error("The main post on spigotmc and please download the correct version.");
            getWgrpBukkitPlugin().getServer().getPluginManager().disablePlugin(wgrpBukkitPlugin);
        }
    }

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
    public PluginManager getPluginManager() {
        return this.wgrpBukkitPlugin.getServer().getPluginManager();
    }

    @NotNull
    public String getPluginVersion() {
        return this.wgrpBukkitPlugin.getDescription().getVersion();
    }

    @NotNull
    public wg7 getWG7() {
        return wg7;
    }
}