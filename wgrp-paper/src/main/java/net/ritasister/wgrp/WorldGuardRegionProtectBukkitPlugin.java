package net.ritasister.wgrp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.ritasister.wgrp.api.CheckIntersection;
import net.ritasister.wgrp.api.handler.LoadHandlers;
import net.ritasister.wgrp.api.logging.JavaPluginLogger;
import net.ritasister.wgrp.api.logging.PluginLogger;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionMetadata;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.loader.WGRPChecker;
import net.ritasister.wgrp.loader.WGRPLoaderCommands;
import net.ritasister.wgrp.loader.WGRPLoaderListeners;
import net.ritasister.wgrp.loader.plugin.LoadPlaceholderAPI;
import net.ritasister.wgrp.loader.plugin.LoadPluginManager;
import net.ritasister.wgrp.loader.plugin.LoadWorldGuard;
import net.ritasister.wgrp.rslibs.UtilCommandWE;
import net.ritasister.wgrp.rslibs.api.RSApiImpl;
import net.ritasister.wgrp.rslibs.api.UtilWEImpl;
import net.ritasister.wgrp.rslibs.api.manager.RegionAdapterManagerPaper;
import net.ritasister.wgrp.rslibs.updater.UpdateNotify;
import net.ritasister.wgrp.util.ServerType;
import net.ritasister.wgrp.util.config.loader.ConfigLoader;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class WorldGuardRegionProtectBukkitPlugin extends WorldGuardRegionProtectPlugin {

    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;
    /**
     * The plugin logger
     */
    private final PluginLogger logger;
    /**
     * The time when the plugin was enabled
     */
    private Instant startTime;
    //private final BukkitAudiences audiences;
    private ConfigLoader configLoader;
    private RSApiImpl rsApi;
    private RegionAdapterManagerPaper regionAdapter;

    private List<UUID> spyLog;
    private UpdateNotify updateNotify;
    private UtilWEImpl playerUtilWE;

    private EntityCheckType entityCheckType;
    private CheckIntersection checkIntersection;
    private MessagingService messagingService;

    public WorldGuardRegionProtectBukkitPlugin(final @NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        super(wgrpBukkitBase.getDescription().getVersion(), ServerType.PAPER);
        this.wgrpBukkitBase = wgrpBukkitBase;
        this.logger = new JavaPluginLogger(wgrpBukkitBase.getLogger());
        load();
    }

    public void load() {
        this.spyLog = new ArrayList<>();
        configLoader = new ConfigLoader();
        configLoader.initConfig(this);

        rsApi = new RSApiImpl(this);

        WGRPChecker wgrpChecker = new WGRPChecker(this);
        wgrpChecker.checkStartUpVersionServer();
        wgrpChecker.checkIfRunningOnPaper();

        loadMetrics();
        loadAnotherClassAndMethods();

        wgrpChecker.notifyAboutBuild();

        updateNotify = new UpdateNotify(wgrpBukkitBase, this);
        updateNotify.checkUpdateNotify(wgrpBukkitBase.getPluginMeta().getVersion());
    }

    public void unLoad() {
    }

    private void loadAnotherClassAndMethods() {
        LoadPluginManager loadWorldGuard = new LoadWorldGuard(this);
        loadWorldGuard.loadPlugin();

        LoadPluginManager loadPlaceholderAPI = new LoadPlaceholderAPI(this);
        loadPlaceholderAPI.loadPlugin();

        this.regionAdapter = new RegionAdapterManagerPaper();

        playerUtilWE = new UtilWEImpl(this);
        playerUtilWE.setUpWorldGuardVersionSeven();

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

    public RSApiImpl getRsApi() {
        return rsApi;
    }

    @Override
    public RegionAdapterManagerPaper getRegionAdapter() {
        return regionAdapter;
    }

    @Contract(pure = true)
    @Override
    public @Nullable WorldGuardRegionMetadata getWorldGuardMetadata() {
        return null;
    }

    public void messageToCommandSender(final @NotNull CommandSender commandSender, final String message) {
        var miniMessage = MiniMessage.miniMessage();
        Component parsed = miniMessage.deserialize(message);
        commandSender.sendMessage(parsed);
    }

    @Override
    public PluginLogger getPluginLogger() {
        return this.logger;
    }

    @Override
    public @Nullable EntityCheckType getEntityChecker() {
        return entityCheckType;
    }

    @Override
    public MessagingService getMessagingService() {
        return messagingService;
    }

    public CheckIntersection getCheckIntersection() {
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
