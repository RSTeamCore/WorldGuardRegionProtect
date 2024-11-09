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
     * Gets the platform type this instance of LuckPerms is running on.
     *
     * @return the platform type
     */
    Platform.Type getType();

    /**
     * Gets a wrapped logger instance for the platform.
     *
     * @return the plugin's logger
     */
    PluginLogger getLogger();

    EntityCheckType getEntityChecker();

    RegionAdapterManager getRegionAdapter();

    WorldGuardRegionMetadata getWorldGuardMetadata();

    Platform getPlatform();

    MessagingService getMessagingService();

    CheckIntersection getCheckIntersection();


}
