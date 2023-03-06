package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.WGRPLoadDataBase;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.loader.impl.InitializationImpl;
import net.ritasister.wgrp.pluginloader.LoadPlaceholderAPI;
import net.ritasister.wgrp.pluginloader.LoadWorldGuard;
import net.ritasister.wgrp.pluginloader.interfaces.LoadPlugin;
import net.ritasister.wgrp.rslibs.api.CommandWEImpl;
import net.ritasister.wgrp.rslibs.api.RSApi;
import net.ritasister.wgrp.rslibs.api.RSRegionImpl;
import net.ritasister.wgrp.rslibs.api.RSStorage;
import net.ritasister.wgrp.rslibs.util.updater.UpdateNotify;
import net.ritasister.wgrp.util.config.loader.ConfigLoader;
import net.rsteamcore.config.Container;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class WGRPInitialization extends InitializationImpl<WorldGuardRegionProtect> {

    @Override
    public void wgrpInit(WorldGuardRegionProtect wgRegionProtect) {
        WGRPChecker wgrpChecker = new WGRPChecker(wgRegionProtect);
        wgrpChecker.checkStartUpVersionServer();
        wgrpChecker.checkForUsePermissionsEx();

        WGRPContainer wgrpContainer = wgRegionProtect.getWgrpContainer();

        wgrpContainer.updateNotify = new UpdateNotify(wgRegionProtect.getWGRPBukkitPlugin());
        wgrpContainer.rsApi = new RSApi(wgRegionProtect);

        loadMetrics(wgRegionProtect);
        loadAnotherClassAndMethods(wgRegionProtect, wgrpContainer);

        WGRPLoadDataBase wgrpLoadDataBase = new WGRPLoadDataBase(wgRegionProtect);
        wgrpLoadDataBase.loadDataBase();

        notifyAboutBuild(wgrpContainer);

        wgrpContainer.updateNotify.checkUpdateNotify(wgrpContainer.getPluginMeta());
    }

    private void loadAnotherClassAndMethods(WorldGuardRegionProtect wgRegionProtect, @NotNull WGRPContainer wgrpContainer) {
        //Libs for this plugin.
        LoadPlugin loadWorldGuard = new LoadWorldGuard();
        loadWorldGuard.loadPlugin();

        LoadPlugin loadPlaceholderAPI = new LoadPlaceholderAPI(wgRegionProtect);
        loadPlaceholderAPI.loadPlugin();

        //Configs.
        wgrpContainer.configLoader = new ConfigLoader();
        if (wgrpContainer.getConfigLoader() != null) {
            wgrpContainer.getConfigLoader().initConfig(wgRegionProtect, wgRegionProtect.getWGRPBukkitPlugin());
        }

        //Api for Regions.
        wgrpContainer.rsRegion = new RSRegionImpl();

        //Parameters for to intercept commands from WE or FAWE.
        wgrpContainer.commandWE = new CommandWEImpl(wgRegionProtect);
        wgrpContainer.wg = wgrpContainer.getCommandWE().setUpWorldGuardVersionSeven();

        //Fields for database.
        wgrpContainer.rsStorage = new RSStorage();
    }

    public void notifyAboutBuild(@NotNull WGRPContainer wgrpContainer) {
        String pluginVersion = wgrpContainer.getPluginVersion();
        Container container = wgrpContainer.getMessages();
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

    public void loadMetrics(@NotNull WorldGuardRegionProtect worldGuardRegionProtect) {
        int pluginId = 12975;
        new Metrics(worldGuardRegionProtect.getWGRPBukkitPlugin(), pluginId);
    }

}
