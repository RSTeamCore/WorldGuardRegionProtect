package net.ritasister.wgrp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import net.ritasister.wgrp.rslibs.api.*;
import net.ritasister.wgrp.rslibs.datasource.Storage;
import net.ritasister.wgrp.rslibs.util.UpdateChecker;
import net.ritasister.wgrp.rslibs.util.wg.Iwg;
import net.ritasister.wgrp.util.UtilConfig;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WorldGuardRegionProtect implements IWGRPBukkit {

    private final WGRPBukkitPlugin wgrpBukkitPlugin;

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
        this.rsApi = new RSApi(this);
        this.rsLogger = new RSLogger();
        this.checkStartUpVersionServer();
        this.loadMetrics();
        this.loadAnotherClassAndMethods();
        this.loadDataBase();
        this.notifyAboutBuild();
        this.checkUpdateNotifyConsole();
    }

    public void unload() {
        if(getUtilConfig() == null) {
            try {
                this.getUtilConfig().getConfig().saveConfig();
            } catch (NullPointerException ignored) {
                getLogger().info("Cannot save config, because config is not loaded!");
            }
        }
    }

    private void loadAnotherClassAndMethods() {
        //Libs of WorldGuard.
        this.loadLibs = new LoadLibs(this);
        this.getLoadLibs().loadPlaceholderAPI();
        this.getLoadLibs().loadWorldGuard();

        //Configs.
        this.utilConfig = new UtilConfig();
        if (this.getUtilConfig() != null) {
            this.getUtilConfig().initConfig(this, getWGRPBukkitPlugin());
        }

        //API for Regions.
        this.rsRegion = new RSRegion();

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

    public void checkUpdateNotifyConsole() {
        new UpdateChecker(this.getWGRPBukkitPlugin(), 81321).getVersion(version -> {
            if (this.getWGRPBukkitPlugin().getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getConsoleSender().sendMessage(String.format("""
                ========[WorldGuardRegionProtect]========
                          Current version: %s
                       You are using the latest version.
                =======================================
                """, version));
            } else {
                Bukkit.getConsoleSender().sendMessage(String.format("""
                ========[WorldGuardRegionProtect]========
                 There is a new version update available.
                        Current version: %s
                          New version: %s
                   Please, download new version here
                 https://www.spigotmc.org/resources/81321/
                =======================================
                """, this.getPluginVersion(), version));
            }
        });
    }

    public void checkUpdateNotifyPlayer(Player player) {
        if(this.getUtilConfig().getConfig().getUpdateChecker()) {
            new UpdateChecker(this.getWGRPBukkitPlugin(), 81321).getVersion(version -> {
                if (this.getWGRPBukkitPlugin().getDescription().getVersion().equalsIgnoreCase(version)) {
                    getRsApi().getMessageToPlayer(player, String.format("""
                            <yellow>========<dark_gray>[<red>WorldGuardRegionProtect<dark_gray>]<yellow>========
                                      <gold>Current version: <aqua>%s
                                   <gold>You are using the latest version.
                            <yellow>=======================================
                            """, version));
                } else {
                    getRsApi().getMessageToPlayer(player, String.format("""
                            <yellow>========<dark_gray>[<red>WorldGuardRegionProtect<dark_gray>]<yellow>========
                            <gold> There is a new version update available.
                            <red>        Current version: <dark_red>%s
                            <dark_aqua>          New version: <aqua>%s
                            <gold>   Please, download new version here
                            <gold> https://www.spigotmc.org/resources/81321/
                            <yellow>=======================================
                            """, this.getPluginVersion(), version));
                }
            });
        }
    }

    private void checkStartUpVersionServer() {
        if (!this.getRsApi().isVersionSupported()) {
            getLogger().error("""
            This plugin version works only on 1.19+!
            Please read this thread: https://www.spigotmc.org/resources/81321/
            The main post on spigotmc and please download the correct version.
            """);
            getWGRPBukkitPlugin().getServer().getPluginManager().disablePlugin(wgrpBukkitPlugin);
        }
    }

    public void notifyAboutBuild() {
        if(this.getPluginVersion().contains("alpha") || this.getPluginVersion().contains("beta") || this.getPluginVersion().contains("pre")) {
            this.getLogger().warn("""
                        This is a test build. This building may be unstable!
                        When reporting a bug:
                        Use the issue tracker! Don't report bugs in the reviews.
                        Please search for duplicates before reporting a new https://github.com/RSTeamCore/WorldGuardRegionProtect/issues!
                        Provide as much information as possible.
                        Provide your WorldGuardRegionProtect version and Spigot/Paper version.
                        Provide any stack traces or "errors" using pastebin.
                       """);
        } else {
            this.getLogger().info("This is the latest stable building.");
        }
        Bukkit.getConsoleSender().sendMessage(String.format("""
                        Using %s language version %s
                        Author of this localization - %s
                        """,
                getUtilConfig().getMessages().get("langTitle.language"),
                getUtilConfig().getMessages().get("langTitle.version"),
                getUtilConfig().getMessages().get("langTitle.author")));
    }

    public void loadDataBase() {
        if(this.getUtilConfig().getConfig().getDataBaseEnable()) {
            final long durationTimeStart = System.currentTimeMillis();
            this.getRsStorage().dbLogsSource = new Storage(this);
            this.getRsStorage().dbLogs.clear();
            if (this.getRsStorage().dbLogsSource.load()) {
                this.getLogger().info("[DataBase] The database is loaded.");
                this.postEnable();
                this.getLogger().info(String.format(
                        "[DataBase] Startup duration: %s ms.", System.currentTimeMillis() - durationTimeStart));
            }
        }
    }

    public void postEnable() {
        this.getWGRPBukkitPlugin().getServer().getScheduler().cancelTasks(this.getWGRPBukkitPlugin());
        if (this.getUtilConfig().getConfig().getMySQLSettings().getIntervalReload() > 0) {
            this.getRsStorage().dbLogsSource.loadAsync();
            this.getLogger().info("[DataBase] The database is loaded asynchronously.");
        }
    }

    public void loadMetrics() {
        int pluginId = 12975;
        new Metrics(this.getWGRPBukkitPlugin(), pluginId);
    }

    @Override
    public WGRPBukkitPlugin getWGRPBukkitPlugin() {
        return this.wgrpBukkitPlugin;
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

    public RSLogger getLogger() {
        return this.rsLogger;
    }

    @NotNull
    public Iwg getIwg() {
        return this.Iwg;
    }
}