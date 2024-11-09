package net.ritasister.wgrp;

import net.ritasister.wgrp.api.CheckIntersection;
import net.ritasister.wgrp.api.logging.PluginLogger;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionMetadata;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.platform.Platform;

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
     * Gets a wrapped logger instance for the platform.
     *
     * @return the plugin's logger
     */
    Platform getPlatform();

    /**
     * Gets a wrapped logger instance for the platform.
     *
     * @return the plugin's logger
     */
    PluginLogger getLogger();

    /**
     * Gets a wrapped entity checker instance for the platform.
     *
     * @return the plugin's logger
     */
    <E, T>EntityCheckType<E, T> getEntityChecker();

    /**
     * Gets a wrapped region adapter instance for the platform.
     *
     * @return the plugin's logger
     */
    <L, P, R>RegionAdapterManager<L, P, R> getRegionAdapter();

    /**
     * Gets a wrapped metadata instance for the platform.
     *
     * @return the plugin's logger
     */
    WorldGuardRegionMetadata getWorldGuardMetadata();

    /**
     * Gets a wrapped messaging service instance for the platform.
     *
     * @return the plugin's logger
     */
    <P>MessagingService<P> getMessagingService();

    /**
     * Gets a wrapped check intersection instance for the platform.
     *
     * @return the plugin's logger
     */
    <P>CheckIntersection<P> getCheckIntersection();

}
