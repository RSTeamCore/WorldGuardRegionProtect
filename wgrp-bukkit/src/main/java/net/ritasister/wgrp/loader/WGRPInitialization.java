package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.WGRPLoadDataBase;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.loader.plugin.LoadPlaceholderAPI;
import net.ritasister.wgrp.loader.plugin.LoadPlugin;
import net.ritasister.wgrp.loader.plugin.LoadWorldGuard;
import net.ritasister.wgrp.rslibs.api.CommandWEImpl;
import net.ritasister.wgrp.rslibs.api.RSApi;
import net.ritasister.wgrp.rslibs.api.RSRegionImpl;
import net.ritasister.wgrp.rslibs.api.RSStorage;
import net.ritasister.wgrp.rslibs.util.updater.UpdateNotify;
import net.ritasister.wgrp.util.config.loader.ConfigLoader;
import org.bstats.bukkit.Metrics;
import org.jetbrains.annotations.NotNull;

public class WGRPInitialization extends InitializationImpl<WorldGuardRegionProtect> {

    @Override
    public void wgrpInit(@NotNull WorldGuardRegionProtect wgRegionProtect) {
        WGRPContainer wgrpContainer = wgRegionProtect.getWgrpContainer();
        wgrpContainer.updateNotify = new UpdateNotify(wgRegionProtect.getWGRPBukkitPlugin());
        wgrpContainer.rsApi = new RSApi(wgRegionProtect);

        WGRPChecker wgrpChecker = new WGRPChecker(wgRegionProtect);
        wgrpChecker.checkStartUpVersionServer();
        wgrpChecker.checkIfRunningOnPaper();

        loadMetrics(wgRegionProtect);
        loadAnotherClassAndMethods(wgRegionProtect, wgrpContainer);

        WGRPLoadDataBase wgrpLoadDataBase = new WGRPLoadDataBase(wgRegionProtect);
        wgrpLoadDataBase.loadDataBase();

        wgrpChecker.notifyAboutBuild(wgrpContainer);

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

    public void loadMetrics(@NotNull WorldGuardRegionProtect worldGuardRegionProtect) {
        int pluginId = 12975;
        new Metrics(worldGuardRegionProtect.getWGRPBukkitPlugin(), pluginId);
    }

}
