package wgrp;

import com.sk89q.worldedit.regions.Region;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.ritasister.wgrp.api.CheckIntersection;
import net.ritasister.wgrp.api.RegionAdapter;
import net.ritasister.wgrp.api.WorldGuardRegionMetadata;
import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import org.bstats.bukkit.Metrics;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wgrp.loader.LoadHandlers;
import wgrp.loader.WGRPChecker;
import wgrp.loader.WGRPLoadDataBase;
import wgrp.loader.WGRPLoaderCommands;
import wgrp.loader.WGRPLoaderListeners;
import wgrp.loader.plugin.LoadPlaceholderAPI;
import wgrp.loader.plugin.LoadPluginManager;
import wgrp.loader.plugin.LoadWorldGuard;
import wgrp.rslibs.UtilCommandWE;
import wgrp.rslibs.api.RSApiImpl;
import wgrp.rslibs.api.RSStorage;
import wgrp.rslibs.api.UtilWEImpl;
import wgrp.rslibs.util.updater.UpdateNotify;
import wgrp.util.ServerType;
import wgrp.util.config.loader.ConfigLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldGuardRegionProtectBukkitPlugin extends WorldGuardRegionProtectPlugin {

    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;
    private final BukkitAudiences audiences;
    private ConfigLoader configLoader;
    private RSApiImpl rsApi;
    private final RegionAdapter<Location, Player, Region> regionAdapter;
    private RSStorage rsStorage;
    private final CheckIntersection<Player> checkIntersection;
    private List<UUID> spyLog;
    private UpdateNotify updateNotify;
    private UtilCommandWE playerUtilWE;

    public WorldGuardRegionProtectBukkitPlugin(
            final @NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase,
            final RegionAdapter<Location, Player, Region> adapter,
            final CheckIntersection<Player> intersection
    ) {
        super(wgrpBukkitBase.getDescription().getVersion(), ServerType.SPIGOT);
        this.wgrpBukkitBase = wgrpBukkitBase;
        this.audiences = BukkitAudiences.create(wgrpBukkitBase);
        regionAdapter = adapter;
        checkIntersection = intersection;
        load();
    }

    public void load() {
        this.spyLog = new ArrayList<>();

        configLoader = new ConfigLoader();
        configLoader.initConfig(this.wgrpBukkitBase);

        rsApi = new RSApiImpl(this);

        WGRPChecker wgrpChecker = new WGRPChecker(this.wgrpBukkitBase);
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
    public RegionAdapter<Location, Player, Region> getRegionAdapter() {
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
