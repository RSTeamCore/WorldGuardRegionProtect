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
import net.ritasister.wgrp.loader.plugin.LoadWorldGuard;
import net.ritasister.wgrp.rslibs.UtilCommandWE;
import net.ritasister.wgrp.rslibs.api.RSApiImpl;
import net.ritasister.wgrp.rslibs.api.UtilWEImpl;
import net.ritasister.wgrp.rslibs.api.manager.RegionAdapterManagerPaper;
import net.ritasister.wgrp.rslibs.updater.UpdateNotify;
import net.ritasister.wgrp.util.ServerType;
import net.ritasister.wgrp.util.config.ParamsVersionCheckImpl;
import net.ritasister.wgrp.util.config.loader.ConfigLoader;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldGuardRegionProtectBukkitPlugin extends WorldGuardRegionProtectPlugin {

    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;
    private final PluginLogger logger;
    private ConfigLoader configLoader;
    private RSApiImpl rsApi;
    private RegionAdapterManagerPaper regionAdapter;
    private WorldGuardRegionMetadata worldGuardRegionMetadata;

    private List<UUID> spyLog;
    private UpdateNotify updateNotify;
    private UtilWEImpl playerUtilWE;

    private EntityCheckType entityCheckType;
    private CheckIntersection checkIntersection;
    private MessagingService messagingService;

    public WorldGuardRegionProtectBukkitPlugin(final @NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        super(wgrpBukkitBase.getPluginMeta().getVersion(), ServerType.PAPER);
        this.wgrpBukkitBase = wgrpBukkitBase;
        this.logger = new JavaPluginLogger(wgrpBukkitBase.getLogger());
        load();
    }

    public void load() {
        this.spyLog = new ArrayList<>();
        configLoader = new ConfigLoader();
        rsApi = new RSApiImpl(this, new ParamsVersionCheckImpl());
        configLoader.initConfig(this);

        final WGRPChecker wgrpChecker = new WGRPChecker(this);
        wgrpChecker.checkStartUpVersionServer();
        wgrpChecker.detectPlatformRun();

        loadMetrics();
        loadAnotherClassAndMethods();

        wgrpChecker.notifyAboutBuild();

        updateNotify = new UpdateNotify(wgrpBukkitBase, this);
        updateNotify.checkUpdateNotify(wgrpBukkitBase.getPluginMeta().getVersion());
    }

    public void unLoad() {
        configLoader.getConfig().saveConfig();
    }

    private void loadAnotherClassAndMethods() {
        final LoadWorldGuard loadWorldGuard = new LoadWorldGuard(this);
        loadWorldGuard.loadPlugin();

        final LoadPlaceholderAPI loadPlaceholderAPI = new LoadPlaceholderAPI(this);
        loadPlaceholderAPI.loadPlugin();

        this.regionAdapter = new RegionAdapterManagerPaper();

        playerUtilWE = new UtilWEImpl(this);
        checkIntersection = playerUtilWE.setUpWorldGuardVersionSeven();

        final LoadHandlers<WorldGuardRegionProtectBukkitPlugin> loaderCommands = new WGRPLoaderCommands();
        loaderCommands.loadHandler(this);

        final LoadHandlers<WorldGuardRegionProtectBukkitPlugin> loaderListeners = new WGRPLoaderListeners();
        loaderListeners.loadHandler(this);
    }

    public void loadMetrics() {
        final int pluginId = 12975;
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
        return this.regionAdapter;
    }

    @Override
    public WorldGuardRegionMetadata getWorldGuardMetadata() {
        return this.worldGuardRegionMetadata;
    }

    public void messageToCommandSender(final @NotNull CommandSender commandSender, final String message) {
        final var miniMessage = MiniMessage.miniMessage();
        final Component parsed = miniMessage.deserialize(message);
        commandSender.sendMessage(parsed);
    }

    @Override
    public PluginLogger getPluginLogger() {
        return this.logger;
    }

    @Override
    public EntityCheckType getEntityChecker() {
        return this.entityCheckType;
    }

    @Override
    public MessagingService getMessagingService() {
        return this.messagingService;
    }

    public CheckIntersection getCheckIntersection() {
        return this.checkIntersection;
    }

    public UtilCommandWE getPlayerUtilWE() {
        return this.playerUtilWE;
    }

    public UpdateNotify getUpdateNotify() {
        return this.updateNotify;
    }

    public ConfigLoader getConfigLoader() {
        return this.configLoader;
    }

}
