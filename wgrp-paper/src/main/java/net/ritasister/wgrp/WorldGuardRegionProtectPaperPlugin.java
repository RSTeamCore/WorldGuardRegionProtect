package net.ritasister.wgrp;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.api.config.provider.ConfigProvider;
import net.ritasister.wgrp.api.config.provider.MessageProvider;
import net.ritasister.wgrp.api.config.version.ConfigVersionReader;
import net.ritasister.wgrp.api.config.version.VersionChecker;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.api.manager.regions.RegionAction;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionProtectMetadata;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.model.permissions.PermissionCheck;
import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import net.ritasister.wgrp.handler.TaskHandler;
import net.ritasister.wgrp.loader.PluginDisabler;
import net.ritasister.wgrp.loader.WGRPCompatibilityCheck;
import net.ritasister.wgrp.loader.WGRPLoaderHandlers;
import net.ritasister.wgrp.loader.plugin.LoadPlaceholderAPI;
import net.ritasister.wgrp.loader.plugin.LoadWorldGuard;
import net.ritasister.wgrp.plugin.AbstractWorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.rslibs.UtilCommandWE;
import net.ritasister.wgrp.rslibs.api.PlayerPermissionsImpl;
import net.ritasister.wgrp.rslibs.api.RSApiImpl;
import net.ritasister.wgrp.rslibs.api.UtilWEImpl;
import net.ritasister.wgrp.rslibs.api.manager.region.RegionAdapterManagerPaper;
import net.ritasister.wgrp.rslibs.api.manager.tools.ToolsAdapterManagerPaper;
import net.ritasister.wgrp.rslibs.updater.UpdateNotify;
import net.ritasister.wgrp.rslibs.wg.CheckIntersection;
import net.ritasister.wgrp.util.file.config.provider.ZonedDateProvider;
import net.ritasister.wgrp.util.file.UpdateFile;
import net.ritasister.wgrp.util.file.config.ConfigType;
import net.ritasister.wgrp.util.file.config.files.Config;
import net.ritasister.wgrp.util.file.config.files.Messages;
import net.ritasister.wgrp.util.file.config.loader.ConfigLoader;
import net.ritasister.wgrp.rslibs.updater.UpdateDownloaderGitHub;
import net.ritasister.wgrp.util.file.config.provider.MessagesProvider;
import net.ritasister.wgrp.util.file.config.version.ConfigCheckVersion;
import net.ritasister.wgrp.util.file.config.version.MessageCheckVersion;
import net.ritasister.wgrp.util.file.config.version.VersionUpdateService;
import net.ritasister.wgrp.util.schedulers.FoliaRunnable;
import net.ritasister.wgrp.util.utility.platform.PlatformDetector;
import net.ritasister.wgrp.util.utility.version.MinecraftVersionChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static net.ritasister.wgrp.util.utility.UtilityClass.isClassPresent;

public class WorldGuardRegionProtectPaperPlugin extends AbstractWorldGuardRegionProtectPlugin {

    private final WGRPBootstrap bootstrap;

    private PlatformDetector platformDetector;

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

    private MinecraftVersionChecker versionCheck;

    private ConfigLoader configLoader;
    private ConfigProvider<WorldGuardRegionProtectPaperPlugin, Config> configProvider;
    private MessageProvider<WorldGuardRegionProtectPaperPlugin, Messages> messageProvider;

    public WorldGuardRegionProtectPaperPlugin(WorldGuardRegionProtectPaperBase wgrpBase) {
        this.bootstrap = new WGRPBootstrap(wgrpBase,this);
    }

    public void onEnable() {
        this.load();
        this.adventure = BukkitAudiences.create(this.bootstrap.getLoader());
        this.initializeFields();

        if (compatibleChecking()) {
            return;
        }

        this.initializeMetrics();
        this.loadAnotherClassAndMethods();
        this.logStartupTime();
    }

    private boolean compatibleChecking() {
        final MinecraftVersionChecker versionChecker = this.versionCheck;
        platformDetector = new PlatformDetector(this.getLogger());
        final PluginDisabler pluginDisabler = new PluginDisabler(this);
        final WGRPCompatibilityCheck compatibilityCheck = new WGRPCompatibilityCheck(versionChecker, platformDetector, pluginDisabler);

        return !compatibilityCheck.performCompatibilityChecks();
    }

    private void initializeFields() {

        this.versionCheck = new MinecraftVersionChecker(this.bootstrap);
        this.spyLog = new ArrayList<>();

        configProvider = new net.ritasister.wgrp.util.file.config.provider.ConfigProvider();
        messageProvider = new MessagesProvider();

        configLoader = configLoader();
        configLoader.loadFiles(this);

        this.rsApi = new RSApiImpl(this);
        this.playerPermissions = new PlayerPermissionsImpl();

        this.listenerHandlerMap = new HashMap<>();
        this.commandMap = new HashMap<>();
        this.taskMap = new HashMap<>();

        this.downloader = new UpdateDownloaderGitHub(this);
        this.updateNotify = new UpdateNotify(this);
    }

    private @NonNull ConfigLoader configLoader() {
        final VersionUpdateService versionUpdateService = getVersionUpdateService();

        final VersionChecker<WorldGuardRegionProtectPaperPlugin> configCheckVersion = new ConfigCheckVersion(versionUpdateService);
        final VersionChecker<WorldGuardRegionProtectPaperPlugin> langCheckVersion = new MessageCheckVersion(versionUpdateService);

        return new ConfigLoader(configProvider, messageProvider, configCheckVersion, langCheckVersion);
    }

    private static @NonNull VersionUpdateService getVersionUpdateService() {
        final ConfigVersionReader<ConfigType, YamlConfiguration> versionReader = new net.ritasister.wgrp.util.file.config.version.ConfigVersionReaderImpl();
        final ZonedDateProvider dateProvider = new ZonedDateProvider();

        final UpdateFile updateFile = new UpdateFile(dateProvider);

        return new VersionUpdateService(versionReader, updateFile);
    }

    private void initializeMetrics() {
        final int pluginId = 12975;
        new Metrics(this.bootstrap.getLoader(), pluginId);
    }

    private void logStartupTime() {
        final Duration timeTaken = Duration.between(bootstrap.getStartupTime(), Instant.now());
        getLogger().info("Successfully enabled. (took " + timeTaken.toMillis() + "ms)");
    }

    public void onDisable() {
        try {
            unLoad();
        } catch (Exception exception) {
            this.getLogger().severe("Failed to unload resources: ", exception);
        }
        configProvider.get().saveConfigFiles();
        this.getLogger().info("Saved complete. Good luck and thanks for using WorldGuardRegionProtect!");
    }

    @Override
    protected void registerApiOnPlatform(final WorldGuardRegionProtect api) {
        this.bootstrap.getServer().getServicesManager().register(WorldGuardRegionProtect.class, api, this.bootstrap.getLoader(), ServicePriority.Normal);
    }

    private void loadAnotherClassAndMethods() {
        final LoadWorldGuard loadWorldGuard = new LoadWorldGuard(this);
        loadWorldGuard.loadPlugin();

        final LoadPlaceholderAPI loadPlaceholderAPI = new LoadPlaceholderAPI(this);
        loadPlaceholderAPI.loadPlugin();

        final List<FoliaRunnable> tasks = List.of();

        final List<Handler<?>> handlers = List.of(
                new CommandHandler(this),
                new ListenerHandler(this),
                new TaskHandler(this, tasks)
        );

        new WGRPLoaderHandlers(handlers).loadHandler(this);

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
    public WGRPBootstrap getBootstrap() {
        return this.bootstrap;
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

    public PlatformDetector getPlatformDetector() {
        return platformDetector;
    }

    public UpdateDownloaderGitHub getDownloader() {
        return downloader;
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

    @SuppressWarnings("unchecked")
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
    public RegionAdapterManager<Location, Player, World> getRegionAdapter() {
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

    public MinecraftVersionChecker getVersionCheck() {
        return versionCheck;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MessagingService<Player> getMessagingService() {
        return this.messagingService;
    }

    public UtilCommandWE getPlayerUtilWE() {
        return this.playerUtilWE;
    }

    public UpdateNotify getUpdateNotify() {
        return this.updateNotify;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public ConfigProvider<WorldGuardRegionProtectPaperPlugin, Config> getConfigProvider() {
        return configProvider;
    }

    public MessageProvider<WorldGuardRegionProtectPaperPlugin, Messages> getMessageProvider() {
        return messageProvider;
    }
}
