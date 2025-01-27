package net.ritasister.wgrp;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.api.logging.JavaPluginLogger;
import net.ritasister.wgrp.api.logging.PluginLogger;
import net.ritasister.wgrp.api.manager.regions.RegionAction;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionProtectMetadata;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.model.permissions.PermissionCheck;
import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.loader.WGRPCompatibilityCheck;
import net.ritasister.wgrp.loader.WGRPLoaderHandlers;
import net.ritasister.wgrp.loader.plugin.LoadPlaceholderAPI;
import net.ritasister.wgrp.loader.plugin.LoadWorldGuard;
import net.ritasister.wgrp.rslibs.UtilCommandWE;
import net.ritasister.wgrp.rslibs.api.PlayerPermissionsImpl;
import net.ritasister.wgrp.rslibs.api.RSApiImpl;
import net.ritasister.wgrp.rslibs.api.UtilWEImpl;
import net.ritasister.wgrp.rslibs.manager.region.RegionAdapterManagerPaper;
import net.ritasister.wgrp.rslibs.manager.tools.ToolsAdapterManagerPaper;
import net.ritasister.wgrp.rslibs.updater.UpdateNotify;
import net.ritasister.wgrp.rslibs.wg.CheckIntersection;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import net.ritasister.wgrp.util.file.config.ConfigLoader;
import net.ritasister.wgrp.rslibs.updater.UpdateDownloaderGitHub;
import net.ritasister.wgrp.util.schedulers.FoliaRunnable;
import net.ritasister.wgrp.util.utility.VersionCheck;
import org.bstats.bukkit.Metrics;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static net.ritasister.wgrp.util.utility.UtilityClass.isClassPresent;

public class WorldGuardRegionProtectPaperPlugin extends AbstractWorldGuardRegionProtectPlugin {

    private final WorldGuardRegionProtectPaperBase wgrpPaperBase;
    private final PluginLogger logger;
    private BukkitAudiences adventure;
    private WorldGuardRegionProtectMetadata wgrpMetadata;
    private RegionAction regionAction;
    private RegionAdapterManagerPaper regionAdapter;
    private ToolsAdapterManagerPaper toolsAdapter;
    private CheckIntersection checkIntersection;
    private UtilWEImpl playerUtilWE;
    private RSApiImpl rsApi;
    private PlayerPermissionsImpl playerPermissions;
    private List<UUID> spyLog;
    private EntityCheckType<Entity, EntityType> entityCheckType;
    private MessagingService<Player> messagingService;

    private Map<Class<? extends Listener>, Listener> listenerHandlerMap = new HashMap<>();
    private Map<String, CommandExecutor> commandMap;
    private Map<Class<? extends FoliaRunnable>, FoliaRunnable> taskMap = new HashMap<>();

    private UpdateDownloaderGitHub downloader;
    private UpdateNotify updateNotify;
    private VersionCheck versionCheck;
    private ConfigLoader configLoader;
    private Instant startTime;

    public WorldGuardRegionProtectPaperPlugin(final @NotNull WorldGuardRegionProtectPaperBase wgrpPaperBase) {
        this.wgrpPaperBase = wgrpPaperBase;
        this.logger = new JavaPluginLogger(wgrpPaperBase.getLogger());
    }

    public void onEnable() {
        this.startTime = Instant.now();
        load();
        this.adventure = BukkitAudiences.create(wgrpPaperBase);
        initializeFields();
        WGRPCompatibilityCheck compatibilityCheck =  new WGRPCompatibilityCheck(this);
        if (!compatibilityCheck.performCompatibilityChecks()) {
            return;
        }
        initializeMetrics();
        loadAnotherClassAndMethods();
        compatibilityCheck.notifyAboutBuild();
        this.updateNotify.checkUpdateNotify(wgrpPaperBase.getDescription().getVersion());
        logStartupTime();
    }

    private void initializeFields() {
        this.versionCheck = new VersionCheck(this);
        this.spyLog = new ArrayList<>();

        this.configLoader = new ConfigLoader();
        this.configLoader.initConfig(this);

        this.rsApi = new RSApiImpl(this);
        this.playerPermissions = new PlayerPermissionsImpl();

        this.listenerHandlerMap = new HashMap<>();
        this.commandMap = new HashMap<>();
        this.taskMap = new HashMap<>();

        this.downloader = new UpdateDownloaderGitHub(this);
        this.updateNotify = new UpdateNotify(this);
    }

    private void initializeMetrics() {
        final int pluginId = 12975;
        new Metrics(wgrpPaperBase, pluginId);
    }

    private void logStartupTime() {
        final Duration timeTaken = Duration.between(getStartupTime(), Instant.now());
        getLogger().info("Successfully enabled. (took " + timeTaken.toMillis() + "ms)");
    }

    public void onDisable() {
        try {
            unLoad();
        } catch (Exception exception) {
            this.getLogger().severe("Failed to unload resources: ", exception);
        }
        saveConfigFiles();
        this.getLogger().info("Saved complete. Good luck and thanks for using WorldGuardRegionProtect!");
    }

    private void saveConfigFiles() {
        this.getLogger().info("Saving all configuration files before the plugin shuts down...");
        try {
            for (ConfigFields configFields : ConfigFields.values()) {
                final var path = configFields.getPath();
                final var fields = configFields.get(this);
                if (configLoader != null) {
                    configLoader.getConfig().saveConfig(path, fields);
                }
                this.getLogger().info(String.format("Successfully checked and saved fields: %s", configFields));
            }
        } catch (Exception exception) {
            this.getLogger().severe("Failed to save config.yml! Error: " + exception.getLocalizedMessage());
            this.getLogger().severe("Exception details:", exception);
        }
    }

    @Override
    protected void registerApiOnPlatform(final WorldGuardRegionProtect api) {
        this.wgrpPaperBase.getServer().getServicesManager().register(WorldGuardRegionProtect.class, api, this.wgrpPaperBase, ServicePriority.Normal);
    }

    private void loadAnotherClassAndMethods() {
        new LoadWorldGuard(this).loadPlugin();
        new LoadPlaceholderAPI(this).loadPlugin();

        new WGRPLoaderHandlers().loadHandler(this);

        this.regionAdapter = new RegionAdapterManagerPaper();
        this.toolsAdapter = new ToolsAdapterManagerPaper();

        playerUtilWE = new UtilWEImpl(this);
        checkIntersection = playerUtilWE.setUpWorldGuardVersionSeven();

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
        final Audience audience = adventure.sender(commandSender);
        final var miniMessage = MiniMessage.miniMessage();
        final Component parsed = miniMessage.deserialize(message);
        audience.sendMessage(parsed);
    }

    @Override
    public Platform.Type getType() {
        if (isClassPresent("io.papermc.paper.threadedregions.RegionizedServer")) {
            return Platform.Type.FOLIA;
        } else if (isClassPresent("com.destroystokyo.paper.ParticleBuilder")) {
            return Platform.Type.PAPER;
        } else if (isClassPresent("org.spigotmc.SpigotConfig")) {
            return Platform.Type.SPIGOT;
        } else {
            return Platform.Type.BUKKIT;
        }
    }

    private Instant getStartupTime() {
        return this.startTime;
    }

    public UpdateDownloaderGitHub getDownloader() {
        return downloader;
    }

    @Override
    public @NotNull PluginLogger getLogger() {
        return this.logger;
    }

    @Override
    public PermissionCheck getPermissionCheck() {
        return playerPermissions;
    }

    @Override
    public WorldGuardRegionProtectMetadata getMetaData() {
        return wgrpMetadata;
    }

    @Override
    public RegionAction getRegionAction() {
        return this.regionAction;
    }

    @Override
    public ToolsAdapterManagerPaper getToolsAdapter() {
        return this.toolsAdapter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public EntityCheckType<Entity, EntityType> getEntityChecker() {
        return this.entityCheckType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public RegionAdapterManager<Location, Player> getRegionAdapter() {
        return regionAdapter;
    }

    public Map<Class<? extends Listener>, Listener> getListenerHandlerMap() {
        return listenerHandlerMap;
    }

    public <T extends Listener> T getListener(@NotNull Class<T> listenerClass) {
        return listenerClass.cast(listenerHandlerMap.get(listenerClass));
    }

    public Map<String, CommandExecutor> getCommandMap() {
        return commandMap;
    }

    public Map<Class<? extends FoliaRunnable>, FoliaRunnable> getTaskMap() {
        return taskMap;
    }

    public VersionCheck getVersionCheck() {
        return versionCheck;
    }

    @SuppressWarnings("unchecked")
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
