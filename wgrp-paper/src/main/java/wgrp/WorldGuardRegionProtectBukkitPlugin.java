package wgrp;

import com.sk89q.worldedit.regions.Region;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.ritasister.wgrp.api.CheckIntersection;
import net.ritasister.wgrp.api.RegionAdapter;
import net.ritasister.wgrp.api.WorldGuardRegionMetadata;
import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.api.RSApiImpl;
import net.ritasister.wgrp.core.util.ServerType;
import net.ritasister.wgrp.core.util.config.loader.ConfigLoader;
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
import wgrp.rslibs.CommandWE;
import wgrp.rslibs.api.CommandWEImpl;
import wgrp.rslibs.api.RSStorage;
import wgrp.rslibs.util.updater.UpdateNotify;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldGuardRegionProtectBukkitPlugin extends WorldGuardRegionProtectPlugin {

    //Расширяемый класс для этого класса.
    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;

    private final BukkitAudiences audiences;

    private ConfigLoader configLoader;

    private RSApiImpl rsApi;

    private RegionAdapter<Location, Player, Region> regionAdapter;

    private RSStorage rsStorage;

    private CommandWE commandWE;

    private CheckIntersection<Player> checkIntersection;

    private List<UUID> spyLog;

    private UpdateNotify updateNotify;

    public WorldGuardRegionProtectBukkitPlugin(final @NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        super(wgrpBukkitBase.getDescription().getVersion(), ServerType.SPIGOT);
        this.wgrpBukkitBase = wgrpBukkitBase;
        this.audiences = BukkitAudiences.create(wgrpBukkitBase);
        load();
    }

    public void load() {
        this.spyLog = new ArrayList<>();

        configLoader = new ConfigLoader();
        configLoader.initConfig(wgrpBukkitBase);

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


        commandWE = new CommandWEImpl(this);
        checkIntersection = commandWE.setUpWorldGuardVersionSeven();

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

    @Override
    public RegionAdapter<Location, Player, Region> getRegionAdapter() {
        return regionAdapter;
    }

    @Override
    public WorldGuardRegionMetadata getWorldGuardMetadata() {
        return null;
    }

    @Override
    public void getConfig() {

    }

}
