package net.ritasister.wgrp;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.ritasister.wgrp.api.CheckIntersection;
import net.ritasister.wgrp.api.WorldGuardRegionMetadata;
import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.api.RegionAdapterImpl;
import net.ritasister.wgrp.loader.LoadHandlers;
import net.ritasister.wgrp.loader.WGRPChecker;
import net.ritasister.wgrp.loader.WGRPLoadDataBase;
import net.ritasister.wgrp.loader.WGRPLoaderCommands;
import net.ritasister.wgrp.loader.WGRPLoaderListeners;
import net.ritasister.wgrp.loader.plugin.LoadPlaceholderAPI;
import net.ritasister.wgrp.loader.plugin.LoadPluginManager;
import net.ritasister.wgrp.loader.plugin.LoadWorldGuard;
import net.ritasister.wgrp.rslibs.UtilCommandWE;
import net.ritasister.wgrp.rslibs.api.RSApiImpl;
import net.ritasister.wgrp.rslibs.api.RSStorage;
import net.ritasister.wgrp.rslibs.api.UtilWEImpl;
import net.ritasister.wgrp.rslibs.util.updater.UpdateNotify;
import net.ritasister.wgrp.util.ServerType;
import net.ritasister.wgrp.util.config.loader.ConfigLoader;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldGuardRegionProtectBukkitPlugin extends WorldGuardRegionProtectPlugin {

    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;
    private final BukkitAudiences audiences;
    private ConfigLoader configLoader;
    private RSApiImpl rsApi;
    private RegionAdapterImpl regionAdapter;
    private RSStorage rsStorage;
    private CheckIntersection<Player> checkIntersection;
    private List<UUID> spyLog;
    private UpdateNotify updateNotify;
    private UtilCommandWE playerUtilWE;

    public WorldGuardRegionProtectBukkitPlugin(final @NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        super(wgrpBukkitBase.getDescription().getVersion(), ServerType.SPIGOT);
        this.wgrpBukkitBase = wgrpBukkitBase;
        this.audiences = BukkitAudiences.create(wgrpBukkitBase);
        load();
    }

    public void load() {
        this.spyLog = new ArrayList<>();

        configLoader = new ConfigLoader();
        configLoader.initConfig(this.wgrpBukkitBase);

        rsApi = new RSApiImpl(this);

        WGRPChecker wgrpChecker = new WGRPChecker(this);
        wgrpChecker.checkStartUpVersionServer();
        wgrpChecker.checkIfRunningOnPaper();

        loadMetrics();
        loadAnotherClassAndMethods();

        WGRPLoadDataBase wgrpLoadDataBase = new WGRPLoadDataBase(this);
        wgrpLoadDataBase.loadDataBase();

        wgrpChecker.notifyAboutBuild();

        updateNotify = new UpdateNotify(wgrpBukkitBase, this);
        updateNotify.checkUpdateNotify(wgrpBukkitBase.getDescription());
    }

    private void loadAnotherClassAndMethods() {
        LoadPluginManager loadWorldGuard = new LoadWorldGuard();
        loadWorldGuard.loadPlugin();

        LoadPluginManager loadPlaceholderAPI = new LoadPlaceholderAPI(wgrpBukkitBase);
        loadPlaceholderAPI.loadPlugin();

        this.regionAdapter = new RegionAdapterImpl();

        playerUtilWE = new UtilWEImpl(this);
        playerUtilWE.setUpWorldGuardVersionSeven();

        rsStorage = new RSStorage();

        LoadHandlers<WorldGuardRegionProtectBukkitPlugin> loaderCommands = new WGRPLoaderCommands();
        loaderCommands.loadHandler(this);

        LoadHandlers<WorldGuardRegionProtectBukkitPlugin> loaderListeners = new WGRPLoaderListeners();
        loaderListeners.loadHandler(this);
    }

    public void loadMetrics() {
        int pluginId = 12975;
        new Metrics(wgrpBukkitBase, pluginId);
    }

    public WorldGuardRegionProtectBukkitBase getWgrpBukkitBase() {
        return wgrpBukkitBase;
    }

    public List<UUID> getSpyLog() {
        return spyLog;
    }

    public RSStorage getRsStorage() {
        return rsStorage;
    }

    public RSApiImpl getRsApi() {
        return rsApi;
    }

    public BukkitAudiences getAudiences() {
        return audiences;
    }

    @Override
    public RegionAdapterImpl getRegionAdapter() {
        return regionAdapter;
    }

    @Override
    public WorldGuardRegionMetadata getWorldGuardMetadata() {
        return null;
    }

    public CheckIntersection<Player> getCheckIntersection() {
        return checkIntersection;
    }

    public UtilCommandWE getPlayerUtilWE() {
        return playerUtilWE;
    }

    public UpdateNotify getUpdateNotify() {
        return updateNotify;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

}
