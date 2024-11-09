package net.ritasister.wgrp.api;

import net.ritasister.wgrp.api.logging.PluginLogger;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionMetadata;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.platform.Platform;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * <p>For ease of use, and for platforms without a Service Manager, an instance
 * can also be obtained from the static singleton accessor in
 * {@link WorldGuardRegionProtect}.</p>
 */
public interface WorldGuardRegionProtect {

    /**
     * Gets the {@link WorldGuardRegionMetadata} version of API or plugin.
     */
    WorldGuardRegionMetadata getWorldGuardMetadata();

    /**
     * Gets the {@link PluginLogger} own of plugin loggers.
     *
     * @return the logger server where plugin in running.
     */
    PluginLogger getPluginLogger();

    /**
     * Gets the {@link Platform}, which represents the server platform the
     * plugin is running on.
     *
     * @return the platform
     */
    @NotNull Platform getPlatform();

    /**
     * Gets the {@link RegionAdapterManager} methods for interacting with regions using the
     * WorldGuard plugin with its own API.
     * Gain access to Api region checks, region name retrieval and general interaction with WGRP
     */
    @ApiStatus.Experimental
    <L, P, R>RegionAdapterManager<L, P, R> getRegionAdapterManager();

    /**
     * Gets the {@link EntityCheckType}, which represents the server platform the
     * plugin is running on.
     *
     * @return the entityChecker
     */
    @ApiStatus.Experimental
    <E, T>EntityCheckType<E, T> getEntityCheckerType();

    /**
     * Gets the {@link MessagingService}, various chat messages to the player or in the console.
     */
    @ApiStatus.Experimental
    <P>MessagingService<P> getMessagingService();

    /**
     * Gets the {@link CheckIntersection}, checks methods how player is interacted
     * with WE, FAWE or analog plugins.
     */
    @ApiStatus.Experimental
    <P>CheckIntersection<P> getCheckIntersection();
}
