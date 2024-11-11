package net.ritasister.wgrp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.api.handler.LoadHandlers;
import net.ritasister.wgrp.api.logging.JavaPluginLogger;
import net.ritasister.wgrp.api.logging.PluginLogger;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionMetadata;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.api.regions.RegionAction;
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
import net.ritasister.wgrp.util.config.ConfigFields;
import net.ritasister.wgrp.util.config.ParamsVersionCheckImpl;
import net.ritasister.wgrp.util.config.ConfigLoader;
import net.ritasister.wgrp.util.wg.CheckIntersection;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.ServicePriority;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldGuardRegionProtectPaperPlugin extends AbstractWorldGuardRegionProtectPlugin {

    private final WorldGuardRegionProtectPaperBase wgrpPaperBase;

    private final PluginLogger logger;
    private ConfigLoader configLoader;
    private RSApiImpl rsApi;

    private RegionAdapterManagerPaper regionAdapter;

    private List<UUID> spyLog;
    private UpdateNotify updateNotify;
    private CheckIntersection checkIntersection;
    private UtilWEImpl playerUtilWE;

    private EntityCheckType entityCheckType;
    private MessagingService messagingService;

    public WorldGuardRegionProtectPaperPlugin(final @NotNull WorldGuardRegionProtectPaperBase wgrpPaperBase) {
        this.wgrpPaperBase = wgrpPaperBase;
        this.logger = new JavaPluginLogger(wgrpPaperBase.getLogger());
    }

    public void onEnable() {
        load();
        this.spyLog = new ArrayList<>();
        configLoader = new ConfigLoader();
        rsApi = new RSApiImpl(this, new ParamsVersionCheckImpl());
        configLoader.initConfig(this);

        final WGRPChecker wgrpChecker = new WGRPChecker(this);
        wgrpChecker.checkStartUpVersionServer();
        wgrpChecker.detectWhatIsPlatformRun();

        loadMetrics();
        loadAnotherClassAndMethods();

        wgrpChecker.notifyAboutBuild();

        updateNotify = new UpdateNotify(wgrpPaperBase, this);
        updateNotify.checkUpdateNotify(wgrpPaperBase.getDescription().getVersion());
    }

    public void onDisable() {
        unLoad();
        this.getLogger().info("Saving all configs before shutting down...");
        try {
            for (ConfigFields configFields : ConfigFields.values()) {
                configLoader.getConfig().saveConfig(configFields.getPath(), configFields.get(wgrpPaperBase));
                this.getLogger().info(String.format("Checking and saving fields: %s", configFields));
            }
        } catch (Exception e) {
            this.getLogger().severe("Could not saving config.yml! Please check the error: " + e.getLocalizedMessage());
            e.fillInStackTrace();
        }
        this.getLogger().info("Saved complete. Good luck!");
    }

    @Override
    protected void registerApiOnPlatform(final WorldGuardRegionProtect api) {
        this.wgrpPaperBase.getServer().getServicesManager().register(WorldGuardRegionProtect.class, api, this.wgrpPaperBase, ServicePriority.Normal);
    }

    private void loadAnotherClassAndMethods() {
        final LoadWorldGuard loadWorldGuard = new LoadWorldGuard(this);
        loadWorldGuard.loadPlugin();

        final LoadPlaceholderAPI loadPlaceholderAPI = new LoadPlaceholderAPI(this);
        loadPlaceholderAPI.loadPlugin();

        this.regionAdapter = new RegionAdapterManagerPaper();

        playerUtilWE = new UtilWEImpl(this);
        checkIntersection = playerUtilWE.setUpWorldGuardVersionSeven();

        final LoadHandlers<WorldGuardRegionProtectPaperPlugin> loaderCommands = new WGRPLoaderCommands();
        loaderCommands.loadHandler(this);

        final LoadHandlers<WorldGuardRegionProtectPaperPlugin> loaderListeners = new WGRPLoaderListeners();
        loaderListeners.loadHandler(this);
    }

    public void loadMetrics() {
        final int pluginId = 12975;
        new Metrics(wgrpPaperBase, pluginId);
    }

    public List<UUID> getSpyLog() {
        return spyLog;
    }

    public RSApiImpl getRsApi() {
        return rsApi;
    }

    public WorldGuardRegionProtectPaperBase getWgrpPaperBase() {
        return wgrpPaperBase;
    }

    public CheckIntersection getCheckIntersection() {
        return checkIntersection;
    }

    public void messageToCommandSender(final @NotNull CommandSender commandSender, final String message) {
        final var miniMessage = MiniMessage.miniMessage();
        final Component parsed = miniMessage.deserialize(message);
        commandSender.sendMessage(parsed);
    }

    @Override
    public Platform.Type getType() {
        return Platform.Type.PAPER;
    }

    @Override
    public @NotNull PluginLogger getLogger() {
        return this.logger;
    }

    @Override
    public WorldGuardRegionMetadata getWorldGuardMetadata() {
        return null;
    }

    @Override
    public RegionAction getRegionAction() {
        return null;
    }

    @Override
    public EntityCheckType getEntityChecker() {
        return this.entityCheckType;
    }

    @Override
    public RegionAdapterManager getRegionAdapter() {
        return this.regionAdapter;
    }

    @Override
    public MessagingService getMessagingService() {
        return this.messagingService;
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
