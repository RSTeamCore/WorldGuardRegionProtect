package net.ritasister.wgrp;

import com.google.inject.Inject;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import net.ritasister.wgrp.rslibs.api.*;
import net.ritasister.wgrp.rslibs.datasource.Storage;
import net.ritasister.wgrp.rslibs.util.UpdateChecker;
import net.ritasister.wgrp.rslibs.util.wg.Iwg;
import net.ritasister.wgrp.util.UtilConfig;
import net.ritasister.wgrp.util.config.Message;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldGuardRegionProtect {

    @Inject
    private WGRPBukkitPlugin wgrpBukkitPlugin;

    private Iwg Iwg;

    //DataBase
    private RSStorage rsStorage;
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

    public void load() {
        this.rsApi = new RSApi();
        this.rsLogger = new RSLogger();
        this.checkStartUpVersionServer();
        this.loadMetrics();
        this.loadAnotherClassAndMethods();
        this.loadDataBase();
        this.notifyPreBuild();
        this.checkUpdate();
    }

    private void loadAnotherClassAndMethods() {
        //Libs of WorldGuard.
        this.loadLibs = new LoadLibs();
        this.getLoadLibs().loadPlaceholderAPI();
        this.getLoadLibs().loadWorldGuard();

        //Configs.
        this.utilConfig = new UtilConfig();
        this.getUtilConfig().initConfig(this);

        //API for Regions.
        this.rsRegion = new RSRegion();

        //Parameters for to intercept commands from WE or FAWE.
        this.commandWE = new CommandWE();
        this.Iwg=this.getCommandWE().setUpWorldGuardVersionSeven();

        //Commands and listeners.
        this.loadCommandsAndListener();

        //Fields for database.
        this.rsStorage = new RSStorage();
    }

    private void loadCommandsAndListener() {
        ListenerHandler registerListener = new ListenerHandler();
        registerListener.listenerHandler(this.getPluginManager());
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.commandHandler();
    }

    public void checkUpdate() {
        new UpdateChecker(this.getWgrpBukkitPlugin(), 81321).getVersion(version -> {
            if (this.getWgrpBukkitPlugin().getDescription().getVersion().equalsIgnoreCase(version)) {
                this.getRsApi().getLogger().info("&6==============================================");
                this.getRsApi().getLogger().info("&2Current version: &b<pl_ver>".replace("<pl_ver>", this.getPluginVersion()));
                this.getRsApi().getLogger().info("&2This is the latest version of plugin.");
                this.getRsApi().getLogger().info("&6==============================================");
            } else {
                this.getRsApi().getLogger().info("&6==============================================");
                this.getRsApi().getLogger().info("&eThere is a new version update available.");
                this.getRsApi().getLogger().info("&cCurrent version: &4<pl_ver>".replace("<pl_ver>", this.getPluginVersion()));
                this.getRsApi().getLogger().info("&3New version: &b<new_pl_ver>".replace("<new_pl_ver>", version));
                this.getRsApi().getLogger().info("&ePlease, download new version here:");
                this.getRsApi().getLogger().info("&ehttps://www.spigotmc.org/resources/81321/");
                this.getRsApi().getLogger().info("&6==============================================");
            }
        });
    }

    public void checkUpdateNotifyPlayer(Player player) {
        new UpdateChecker(this.getWgrpBukkitPlugin(), 81321).getVersion(version -> {
            if (this.getWgrpBukkitPlugin().getDescription().getVersion().equalsIgnoreCase(version)) {
                player.sendMessage("""
                &e======&8[&cWorldGuardRegionProtect&8]&e======
                          &6Current version: &b%s
                       &6You are using the latest version.
                &e===================================
                """, version);
            }else{
                player.sendMessage("""
                &e========&8[&cWorldGuardRegionProtect&8]&e========
                &6 There is a new version update available.
                &c        Current version: &4%s
                &3          New version: &b%s
                &6   Please, download new version here
                &6 https://www.spigotmc.org/resources/81321/
                &e=======================================
                """, this.getPluginVersion(), version);
            }
        });
    }

    private void checkStartUpVersionServer() {
        if (!this.getRsApi().isVersionSupported()) {
            getRsApi().getLogger().error("This plugin version works only on 1.19+!");
            getRsApi().getLogger().error("Please read this thread: https://www.spigotmc.org/resources/81321/");
            getRsApi().getLogger().error("The main post on spigotmc and please download the correct version.");
            getWgrpBukkitPlugin().getServer().getPluginManager().disablePlugin(wgrpBukkitPlugin);
        }
    }

    public void notifyPreBuild() {
        if(this.getPluginVersion().contains("pre")) {
            this.getRsApi().getLogger().warn("""
                        This is a test build. This building may be unstable!
                        When reporting a bug:
                        Use the issue tracker! Don't report bugs in the reviews.
                        Please search for duplicates before reporting a new https://github.com/RSTeamCore/WorldGuardRegionProtect/issues!
                        Provide as much information as possible.
                        Provide your WorldGuardRegionProtect version and Spigot/Paper version.
                        Provide any stack traces or "errors" using pastebin.
                       """);
        } else {
            this.getRsApi().getLogger().info("This is the latest stable building.");
        }
        this.getRsApi().getLogger().info(
                String.format("""
                        Using %s language version %s
                        Author of this localization - %s
                        """,
                        Message.getLangProperties().getLanguage(),
                        Message.getLangProperties().getVersion(),
                        Message.getLangProperties().getAuthor()));
    }

    public void loadDataBase() {
        if(this.getUtilConfig().getConfig().getDataBaseEnable()) {
            final long duration_time_start = System.currentTimeMillis();
            this.getRsStorage().dbLogsSource = new Storage(this);
            this.getRsStorage().dbLogs.clear();
            if (this.getRsStorage().dbLogsSource.load()) {
                this.getRsApi().getLogger().info("[DataBase] The player base is loaded.");
                this.postEnable();
                this.getRsApi().getLogger().info("[DataBase] Startup duration: <TIME> мс.".replace("<TIME>", String.valueOf(System.currentTimeMillis() - duration_time_start)));
            }
        }
    }

    public void postEnable() {
        this.getWgrpBukkitPlugin().getServer().getScheduler().cancelTasks(this.getWgrpBukkitPlugin());
        if (this.getUtilConfig().getConfig().getMySQLSettings().getIntervalReload() > 0) {
            this.getRsStorage().dbLogsSource.loadAsync();
            this.getRsApi().getLogger().info("[DataBase] The base is loaded asynchronously.");
        }
    }

    public void loadMetrics() {
        int pluginId = 12975;
        new Metrics(this.getWgrpBukkitPlugin(), pluginId);
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