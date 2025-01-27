package net.ritasister.wgrp;

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
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represent class for any platform.
 */
public interface WorldGuardRegionProtectPlugin {

    /**
     * Gets the platform type this instance of WorldGuardRegionProtect is running on.
     *
     * @return the platform type
     */
    Platform.Type getType();

    /**
     * Gets the {@link PluginLogger} own of plugin loggers.
     *
     * @return the logger server where plugin in running.
     */
    @NonNull PluginLogger getLogger();

    /**
     * Gets a wrapped entity checker instance for the platform.
     *
     * @return the plugin's logger
     */
    <E, T> EntityCheckType<E, T> getEntityChecker();

    /**
     * Retrieves the {@link PermissionCheck} instance that is used to check player and entity permissions.
     *
     * @return the {@link PermissionCheck} instance
     * @since 1.7.1.21
     */
    PermissionCheck getPermissionCheck();

    /**
     * Gets a wrapped region adapter instance for the platform.
     *
     * @return the plugin's logger
     */
    <L, P> RegionAdapterManager<L, P> getRegionAdapter();

    /**
     * Returns the class implementing the WorldGuardRegionProtect on this platform.
     *
     * @return the api
     */
    WorldGuardRegionProtectApiProvider getApiProvider();

    /**
     * Gets a wrapped metadata instance for the platform.
     *
     * @return the plugin's logger
     */
    WorldGuardRegionProtectMetadata getMetaData();

    /**
     * Gets a wrapped messaging service instance for the platform.
     *
     * @return the plugin's logger
     */
    <P> MessagingService<P> getMessagingService();

    RegionAction getRegionAction();

    /**
     * Retrieves the {@link ToolsAdapterManager} instance that is used to manage tool-related operations.
     *
     * @return the {@link ToolsAdapterManager} instance
     * @since 1.8.1.21
     */
    <P> ToolsAdapterManager<P> getToolsAdapter();

    /**
     * Attempts to identify the plugin behind the given classloader.
     *
     * <p>Used for giving more helpful log messages when things break.</p>
     *
     * @param classLoader the classloader to identify
     * @return the name of the classloader source
     * @throws Exception anything
     */
    default @Nullable String identifyClassLoader(ClassLoader classLoader) throws Exception {
        return null;
    }

}
