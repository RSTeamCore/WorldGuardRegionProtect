package net.ritasister.wgrp;

import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.api.WorldGuardRegionProtectProvider;
import net.ritasister.wgrp.api.implementation.ApiEntityChecker;
import net.ritasister.wgrp.api.implementation.ApiPermissionCheck;
import net.ritasister.wgrp.api.implementation.ApiPlatform;
import net.ritasister.wgrp.api.implementation.ApiRegionAction;
import net.ritasister.wgrp.api.implementation.ApiRegionProtect;
import net.ritasister.wgrp.api.implementation.ApiToolsProtect;
import net.ritasister.wgrp.api.logging.PluginLogger;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.api.manager.tools.ToolsAdapterManager;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionProtectMetadata;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.model.permissions.PermissionCheck;
import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.api.manager.regions.RegionAction;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class WorldGuardRegionProtectApiProvider implements WorldGuardRegionProtect {

    private final WorldGuardRegionProtectPlugin plugin;

    private final ApiPlatform platform;
    private final RegionAdapterManager<?, ?> regionAdapterManager;
    private final ToolsAdapterManager<?> toolsAdapterManager;
    private final ApiEntityChecker<?, ?> entityCheckType;
    private final MessagingService<?> messagingService;
    private final PermissionCheck permissionCheck;

    private final RegionAction regionAction;

    public WorldGuardRegionProtectApiProvider(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;

        this.platform = new ApiPlatform(plugin);
        this.regionAdapterManager = new ApiRegionProtect<>(plugin);
        this.toolsAdapterManager = new ApiToolsProtect<>(plugin);
        this.regionAction = new ApiRegionAction(plugin);
        this.entityCheckType = new ApiEntityChecker<>(plugin);
        this.permissionCheck = new ApiPermissionCheck(plugin);

        this.messagingService = plugin.getMessagingService();
    }

    public void ensureApiWasLoadedByPlugin() {
        final WorldGuardRegionProtectPlugin worldGuardRegionProtectPlugin = this.plugin;
        final ClassLoader pluginClassLoader = worldGuardRegionProtectPlugin.getClass().getClassLoader();

        for (Class<?> apiClass : new Class[]{WorldGuardRegionProtect.class, WorldGuardRegionProtectProvider.class}) {
            final ClassLoader apiClassLoader = apiClass.getClassLoader();

            if (!apiClassLoader.equals(pluginClassLoader)) {
                String guilty = "unknown";
                try {
                    guilty = worldGuardRegionProtectPlugin.identifyClassLoader(apiClassLoader);
                } catch (Exception e) {
                    // ignore
                }

                final PluginLogger logger = this.plugin.getLogger();
                logger.warn("It seems that the WorldGuardRegionProtect API has been (class)loaded by a plugin other than " +
                        "WorldGuardRegionProtect!");
                logger.warn("The API was loaded by " + apiClassLoader + " (" + guilty + ") and the " +
                        "WorldGuardRegionProtect plugin was loaded by " + pluginClassLoader.toString() + ".");
                logger.warn("This indicates that the other plugin has incorrectly \"shaded\" the " +
                        "WorldGuardRegionProtect API into its jar file. This can cause errors at runtime and should be fixed.");
                return;
            }
        }
    }

    @Override
    public @NotNull Platform getPlatform() {
        return this.platform;
    }

    @Override
    public @NonNull ToolsAdapterManager getToolsAdapterManager() {
        return this.toolsAdapterManager;
    }

    @Override
    public @NonNull WorldGuardRegionProtectMetadata getMetaData() {
        return this.platform;
    }

    @Override
    public @NonNull RegionAdapterManager getRegionAdapter() {
        return this.regionAdapterManager;
    }

    @Override
    public @NonNull EntityCheckType getEntityCheckerType() {
        return this.entityCheckType;
    }

    @Override
    public @NonNull MessagingService getMessagingService() {
        return this.messagingService;
    }

    @Override
    public @NonNull RegionAction getRegionAction() {
        return this.regionAction;
    }

    @Override
    public PermissionCheck getPermissionCheck() {
        return this.permissionCheck;
    }

}
