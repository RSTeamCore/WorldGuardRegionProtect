package net.ritasister.wgrp.api;

import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionMetadata;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.api.manager.regions.RegionAction;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * <p>For ease of use, and for platforms without a Service Manager, an instance
 * can also be obtained from the static singleton accessor in
 * {@link WorldGuardRegionProtectProvider}.</p>
 */
public interface WorldGuardRegionProtect {

    /**
     * Gets the {@link WorldGuardRegionMetadata} version of API or plugin.
     */
    @NonNull WorldGuardRegionMetadata getWorldGuardMetadata();

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
    @NonNull <L, P, R> RegionAdapterManager<L, P> getRegionAdapter();

    /**
     * Gets the {@link EntityCheckType}, which represents the server platform the
     * plugin is running on.
     *
     * @return the entityChecker
     */
    @ApiStatus.Experimental
    @NonNull <E, T> EntityCheckType<E, T> getEntityCheckerType();

    /**
     * Gets the {@link MessagingService}, various chat messages to the player or in the console.
     */
    @ApiStatus.Experimental
    @NonNull <P> MessagingService<P> getMessagingService();

    @ApiStatus.Experimental
    @NonNull RegionAction getRegionAction();

}
