package net.ritasister.wgrp;

import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.ritasister.wgrp.loader.LoadHandlers;
import net.ritasister.wgrp.loader.WGRPChecker;
import net.ritasister.wgrp.loader.WGRPLoadDataBase;
import net.ritasister.wgrp.loader.WGRPLoaderCommands;
import net.ritasister.wgrp.loader.WGRPLoaderListeners;
import net.ritasister.wgrp.loader.plugin.LoadPlaceholderAPI;
import net.ritasister.wgrp.loader.plugin.LoadPluginManager;
import net.ritasister.wgrp.loader.plugin.LoadWorldGuard;
import net.ritasister.wgrp.rslibs.api.CommandWEImpl;
import net.ritasister.wgrp.rslibs.api.RSApi;
import net.ritasister.wgrp.rslibs.api.RSRegionImpl;
import net.ritasister.wgrp.rslibs.api.RSStorage;
import net.ritasister.wgrp.rslibs.api.interfaces.CommandWE;
import net.ritasister.wgrp.rslibs.api.interfaces.RSRegion;
import net.ritasister.wgrp.rslibs.util.ServerType;
import net.ritasister.wgrp.rslibs.util.updater.UpdateNotify;
import net.ritasister.wgrp.rslibs.util.wg.WG;
import net.ritasister.wgrp.util.config.loader.ConfigLoader;
import org.bstats.bukkit.Metrics;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class WorldGuardRegionProtectBukkitPlugin extends WorldGuardRegionProtectPlugin {

    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;

    private final BukkitAudiences audiences;

    private ConfigLoader configLoader;

    private RSRegion rsRegion;

    private RSApi rsApi;

    private RSStorage rsStorage;

    private CommandWE commandWE;

    private WG wg;

    private List<UUID> spyLog;

    private UpdateNotify updateNotify;

    public WorldGuardRegionProtectBukkitPlugin(final @NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        super(ServerType.SPIGOT);
        this.wgrpBukkitBase = wgrpBukkitBase;
        this.audiences = BukkitAudiences.create(wgrpBukkitBase);
        load();
    }

    public void load() {
        this.spyLog = new ArrayList<>();

        configLoader = new ConfigLoader();
        configLoader.initConfig(wgrpBukkitBase);

        rsApi = new RSApi(this);

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

        rsRegion = new RSRegionImpl();

        commandWE = new CommandWEImpl(this);
        wg = getCommandWE().setUpWorldGuardVersionSeven();

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

}
