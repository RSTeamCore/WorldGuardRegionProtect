package net.ritasister.wgrp.api;

import net.ritasister.wgrp.api.manager.regions.RegionAction;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.api.manager.tools.ToolsAdapterManager;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionMetadata;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.platform.Platform;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * <p>The main interface for interacting with the WorldGuard Region Protect (WGRP) API.
 * Provides access to various managers and utilities for region interaction,
 * metadata retrieval, platform information, and more.</p>
 *
 * <p>For platforms without a Service Manager, this interface can be accessed through
 * the static singleton accessor in {@link WorldGuardRegionProtectProvider}.</p>
 */
public interface WorldGuardRegionProtect {

    /**
     * Retrieves the metadata for the WorldGuard Region Protect plugin.
     * This includes versioning information and other metadata details.
     *
     * @return the {@link WorldGuardRegionMetadata} instance containing plugin metadata.
     */
    @NonNull
    WorldGuardRegionMetadata getWorldGuardMetadata();

    /**
     * Provides the {@link Platform} the plugin is running on.
     * This includes details about the server platform type and its version.
     *
     * @return the platform information.
     */
    @NotNull Platform getPlatform();

    /**
     * Retrieves the {@link RegionAdapterManager} for interacting with regions using the
     * WorldGuard API. This manager offers methods to perform region-based operations,
     * such as membership checks, ownership validation, and region name retrieval.
     *
     * @return the region adapter manager.
     */
    @ApiStatus.Experimental
    @NonNull
    <L, P> RegionAdapterManager<L, P> getRegionAdapter();

    /**
     * Provides access to the {@link ToolsAdapterManager}, which enables utility methods
     * for interacting with the WorldEdit API. Tools like the Super Pickaxe can
     * be managed using this interface.
     *
     * @return the tools adapter manager.
     */
    @ApiStatus.Experimental
    @NonNull
    <P> ToolsAdapterManager<P> getToolsAdapterManager();

    /**
     * Retrieves the {@link EntityCheckType}, which defines the types of entity checks
     * supported by the plugin. This can be used for validating or interacting with
     * different entities in the game.
     *
     * @return the entity checker type.
     */
    @ApiStatus.Experimental
    @NonNull
    EntityCheckType getEntityCheckerType();

    /**
     * Retrieves the {@link MessagingService} responsible for message handling.
     * This service allows sending chat messages to players or logging to the console.
     *
     * @param <P> the type representing the sender or recipient of the message.
     * @return the messaging service.
     */
    @ApiStatus.Experimental
    @NonNull
    <P> MessagingService<P> getMessagingService();

    /**
     * Retrieves the {@link RegionAction} instance for interacting with predefined
     * region actions, such as breaking, placing, or interacting within a region.
     *
     * @return the region action manager.
     */
    @ApiStatus.Experimental
    @NonNull
    RegionAction getRegionAction();

}
