package net.ritasister.wgrp;

import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import net.ritasister.wgrp.rslibs.api.*;
import net.ritasister.wgrp.rslibs.datasource.Storage;
import net.ritasister.wgrp.rslibs.util.UpdateChecker;
import net.ritasister.wgrp.rslibs.util.wg.Iwg;
import net.ritasister.wgrp.util.UtilConfig;
import net.ritasister.wgrp.util.config.Message;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldGuardRegionProtect implements IWGRPBukkit {

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
        this.rsApi = new RSApi(this);
        this.rsLogger = new RSLogger();
        this.checkStartUpVersionServer();
        this.loadMetrics();
        this.loadAnotherClassAndMethods();
        this.loadDataBase();
        this.notifyPreBuild();
        this.checkUpdate();
    }

    public void unload() {
        if(getUtilConfig() == null) {
            try {
                this.getUtilConfig().getConfig().saveConfig();
            } catch (NullPointerException ignored) {
                getLogger().info(Component.text("Cannot save config, because config is not loaded!"));
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

    public void checkUpdate() {
        new UpdateChecker(this.getWGRPBukkitPlugin(), 81321).getVersion(version -> {
            if (this.getWGRPBukkitPlugin().getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getConsoleSender().sendMessage(String.format("""
                &e======&8[&cWorldGuardRegionProtect&8]&e======
                          &6Current version: &b%s
                       &6You are using the latest version.
                &e===================================
                """, version));
            } else {
                Bukkit.getConsoleSender().sendMessage(String.format("""
                &e========&8[&cWorldGuardRegionProtect&8]&e========
                &6 There is a new version update available.
                &c        Current version: &4%s
                &3          New version: &b%s
                &6   Please, download new version here
                &6 https://www.spigotmc.org/resources/81321/
                &e=======================================
                """, this.getPluginVersion(), version));
            }
        });
    }

    public void checkUpdateNotifyPlayer(Player player) {
        new UpdateChecker(this.getWGRPBukkitPlugin(), 81321).getVersion(version -> {
            if (this.getUtilConfig().getConfig().getUpdateChecker() && this.getWGRPBukkitPlugin().getDescription().getVersion().equalsIgnoreCase(version)) {
                player.sendMessage(String.format("""
                &e======&8[&cWorldGuardRegionProtect&8]&e======
                          &6Current version: &b%s
                       &6You are using the latest version.
                &e===================================
                """, version));
            }else{
                player.sendMessage(String.format("""
                &e========&8[&cWorldGuardRegionProtect&8]&e========
                &6 There is a new version update available.
                &c        Current version: &4%s
                &3          New version: &b%s
                &6   Please, download new version here
                &6 https://www.spigotmc.org/resources/81321/
                &e=======================================
                """, this.getPluginVersion(), version));
            }
        });
    }

    private void checkStartUpVersionServer() {
        if (!this.getRsApi().isVersionSupported()) {
            getLogger().error(Component.text("""
            This plugin version works only on 1.19+!
            Please read this thread: https://www.spigotmc.org/resources/81321/
            The main post on spigotmc and please download the correct version.
            """));
            getWGRPBukkitPlugin().getServer().getPluginManager().disablePlugin(wgrpBukkitPlugin);
        }
    }

    public void notifyPreBuild() {
        if(this.getPluginVersion().contains("pre")) {
            this.getLogger().warn(Component.text("""
                        This is a test build. This building may be unstable!
                        When reporting a bug:
                        Use the issue tracker! Don't report bugs in the reviews.
                        Please search for duplicates before reporting a new https://github.com/RSTeamCore/WorldGuardRegionProtect/issues!
                        Provide as much information as possible.
                        Provide your WorldGuardRegionProtect version and Spigot/Paper version.
                        Provide any stack traces or "errors" using pastebin.
                       """));
        } else {
            this.getLogger().info(Component.text("This is the latest stable building."));
        }
        this.getLogger().info(Component.text(
                String.format("""
                        Using %s language version %s
                        Author of this localization - %s
                        """,
                        Message.getLangProperties().language(),
                        Message.getLangProperties().version(),
                        Message.getLangProperties().author())));
    }

    public void loadDataBase() {
        if(this.getUtilConfig().getConfig().getDataBaseEnable()) {
            final long duration_time_start = System.currentTimeMillis();
            this.getRsStorage().dbLogsSource = new Storage(this);
            this.getRsStorage().dbLogs.clear();
            if (this.getRsStorage().dbLogsSource.load()) {
                this.getLogger().info(Component.text("[DataBase] The database is loaded."));
                this.postEnable();
                this.getLogger().info(Component.text(String.format(
                        "[DataBase] Startup duration: %s мс.", System.currentTimeMillis() - duration_time_start)));
            }
        }
    }

    public void postEnable() {
        this.getWGRPBukkitPlugin().getServer().getScheduler().cancelTasks(this.getWGRPBukkitPlugin());
        if (this.getUtilConfig().getConfig().getMySQLSettings().getIntervalReload() > 0) {
            this.getRsStorage().dbLogsSource.loadAsync();
            this.getLogger().info(Component.text("[DataBase] The database is loaded asynchronously."));
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