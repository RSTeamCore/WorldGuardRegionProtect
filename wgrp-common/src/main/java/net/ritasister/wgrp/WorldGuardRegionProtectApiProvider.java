package net.ritasister.wgrp;

import net.ritasister.wgrp.api.CheckIntersection;
import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.api.logging.PluginLogger;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionMetadata;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.api.regions.RegionAction;
import org.jetbrains.annotations.NotNull;

public class WorldGuardRegionProtectApiProvider implements WorldGuardRegionProtect {

    private final WorldGuardRegionProtectPlugin plugin;

    private final WorldGuardRegionMetadata metadata;
    private final PluginLogger logger;
    private final Platform platform;
    private final RegionAdapterManager regionAdapterManager;
    private final EntityCheckType entityCheckType;
    private final MessagingService messagingService;
    private final CheckIntersection checkIntersection;
    private final RegionAction regionAction;

    public WorldGuardRegionProtectApiProvider(WorldGuardRegionProtectPlugin plugin) {
        this.plugin = plugin;

        this.metadata = plugin.getWorldGuardMetadata();
        this.logger = plugin.getLogger();
        this.platform = plugin.getPlatform();
        this.regionAdapterManager = plugin.getRegionAdapter();
        this.entityCheckType = plugin.getEntityChecker();
        this.messagingService = plugin.getMessagingService();
        this.checkIntersection = plugin.getCheckIntersection();
        this.regionAction = plugin.getRegionAction();
    }

    @Override
    public WorldGuardRegionMetadata getWorldGuardMetadata() {
        return this.metadata;
    }

    @Override
    public PluginLogger getPluginLogger() {
        return this.logger;
    }

    @Override
    public @NotNull Platform getPlatform() {
        return this.platform;
    }

    @Override
    public <L, P, R> RegionAdapterManager<L, P, R> getRegionAdapterManager() {
        return this.regionAdapterManager;
    }

    @Override
    public <E, T> EntityCheckType<E, T> getEntityCheckerType() {
        return this.entityCheckType;
    }

    @Override
    public <P> MessagingService<P> getMessagingService() {
        return this.messagingService;
    }

    @Override
    public <P> CheckIntersection<P> getCheckIntersection() {
        return this.checkIntersection;
    }

    @Override
    public RegionAction getRegionAction() {
        return this.regionAction;
    }

}
