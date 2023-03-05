package net.ritasister.wgrp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import net.ritasister.wgrp.handler.abstracts.AbstractHandler;
import net.ritasister.wgrp.pluginloader.LoadPlaceholderAPI;
import net.ritasister.wgrp.pluginloader.LoadWorldGuard;
import net.ritasister.wgrp.pluginloader.interfaces.LoadPlugin;
import net.ritasister.wgrp.rslibs.api.CommandWEImpl;
import net.ritasister.wgrp.rslibs.api.RSApi;
import net.ritasister.wgrp.rslibs.api.RSRegionImpl;
import net.ritasister.wgrp.rslibs.api.RSStorage;
import net.ritasister.wgrp.rslibs.api.WorldGuardRegionProtectApi;
import net.ritasister.wgrp.rslibs.datasource.Storage;
import net.ritasister.wgrp.rslibs.util.updater.UpdateNotify;
import net.ritasister.wgrp.util.UtilConfig;
import net.rsteamcore.config.Container;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WorldGuardRegionProtect {

    private final WGRPBukkitPlugin wgrpBukkitPlugin;

    private WGRPContainer wgrpContainer;

    public void load() {
        checkForUsePermissionsEx();
        this.wgrpContainer = new WGRPContainer(this);
        getWgrpContainer().updateNotify = new UpdateNotify(getWGRPBukkitPlugin());
        getWgrpContainer().rsApi = new RSApi(this);
        this.checkStartUpVersionServer();
        this.loadMetrics();
        this.loadAnotherClassAndMethods();
        WGRPLoadDataBase wgrpLoadDataBase = new WGRPLoadDataBase(this);
        wgrpLoadDataBase.loadDataBase();
        this.notifyAboutBuild();
        getWgrpContainer().updateNotify.checkUpdateNotify(getWgrpContainer().getPluginMeta());
    }

    public void unLoad() {
        if (getWgrpContainer().getUtilConfig() == null) {
            try {
                getWgrpContainer().getUtilConfig().getConfig().saveConfig();
            } catch (NullPointerException ignored) {
                Bukkit.getLogger().info("Cannot save config, because config is not loaded!");
            }
        }
    }

    private void loadAnotherClassAndMethods() {
        //Libs for this plugin.
        LoadPlugin loadWorldGuard = new LoadWorldGuard();
        loadWorldGuard.loadPlugin();

        LoadPlugin loadPlaceholderAPI = new LoadPlaceholderAPI(this);
        loadPlaceholderAPI.loadPlugin();

        //Configs.
        getWgrpContainer().utilConfig = new UtilConfig();
        if (getWgrpContainer().getUtilConfig() != null) {
            getWgrpContainer().getUtilConfig().initConfig(this, getWGRPBukkitPlugin());
        }

        //Api for Regions.
        getWgrpContainer().rsRegion = new RSRegionImpl();

        //Parameters for to intercept commands from WE or FAWE.
        getWgrpContainer().commandWE = new CommandWEImpl(this);
        getWgrpContainer().wg = getWgrpContainer().getCommandWE().setUpWorldGuardVersionSeven();

        //Commands and listeners.
        this.loadCommandsAndListener();

        //Fields for database.
        getWgrpContainer().rsStorage = new RSStorage();
    }

    private void loadCommandsAndListener() {
        AbstractHandler<PluginManager> registerListener = new ListenerHandler(this);
        registerListener.handle(getWgrpContainer().getPluginManager());

        AbstractHandler<Void> commandHandler = new CommandHandler(this);
        commandHandler.handle();
    }

    private void checkStartUpVersionServer() {
        if (!getWgrpContainer().getRsApi().isVersionSupported()) {
            Bukkit.getLogger().severe("""
                    This plugin version works only on 1.19+!
                    Please read this thread: https://www.spigotmc.org/resources/81321/
                    The main post on spigotmc and please download the correct version for your server version.
                    """);
        }
    }

    private void checkForUsePermissionsEx() {
        if(!getWgrpContainer().getPluginManager().isPluginEnabled("PermissionsEx")) {
            Bukkit.getLogger().severe("""
                    Wea are not supported old version of permissions plugin like PermissionsEx, please use LuckPerms or another.
                    """);
            getWGRPBukkitPlugin().onDisable();
        }
    }

    public void notifyAboutBuild() {
        String pluginVersion = getWgrpContainer().getPluginVersion();
        Container container = getWgrpContainer().getUtilConfig().getMessages();
        if (pluginVersion.contains("alpha")
                || pluginVersion.contains("beta")
                || pluginVersion.contains("pre")) {
            Bukkit.getLogger().warning("""
                     This is a test build. This building may be unstable!
                     When reporting a bug:
                     Use the issue tracker! Don't report bugs in the reviews.
                     Please search for duplicates before reporting a new https://github.com/RSTeamCore/WorldGuardRegionProtect/issues!
                     Provide as much information as possible.
                     Provide your WorldGuardRegionProtect version and Spigot/Paper version.
                     Provide any stack traces or "errors" using pastebin.
                    """);
        } else {
            Bukkit.getLogger().info("This is the latest stable building.");
        }
        Bukkit.getConsoleSender().sendMessage(String.format(""" 
                Using %s language version %s. Author of this localization - %s.
                """, container.get("langTitle.language"), container.get("langTitle.version"), container.get("langTitle.author")
        ));
    }

    public void loadMetrics() {
        int pluginId = 12975;
        new Metrics(this.getWGRPBukkitPlugin(), pluginId);
    }

    public WGRPBukkitPlugin getWGRPBukkitPlugin() {
        return this.wgrpBukkitPlugin;
    }

    public WGRPContainer getWgrpContainer() {
        return wgrpContainer;
    }

}
