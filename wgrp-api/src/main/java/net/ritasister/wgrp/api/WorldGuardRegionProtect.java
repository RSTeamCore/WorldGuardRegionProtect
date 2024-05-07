package net.ritasister.wgrp.api;

import net.ritasister.wgrp.api.logging.PluginLogger;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.regions.RegionAdapter;
import org.jetbrains.annotations.ApiStatus;


public interface WorldGuardRegionProtect {

    /**
     * @return true if WorldGuardRegionProtect is currently enabled
     */
    boolean isWorldGuardRegionProtect();

    /**
     * @param worldGuardRegionProtect true to enable, false to disable maintenance mode
     */
    void setWorldGuardRegionProtect(boolean worldGuardRegionProtect);

    /**
     * @return getWorldGuardMetadata
     */
    WorldGuardRegionMetadata getWorldGuardMetadata();

    /**
     * get message helping
     */
    void messageToCommandSender(Class<?> consoleSender, String format);

    /**
     * Gets the plugin logger
     *
     * @return the logger
     */
    PluginLogger getPluginLogger();

    /**
     * Gain access to Api region checks, region name retrieval and general interaction with WGRP
     */
    @ApiStatus.Experimental
    <Location, Player, Region> RegionAdapter<Location, Player, Region> getRegionAdapter();

    /**
     * Gain access to Api entity checks. Still in development
     */
    @ApiStatus.Experimental
    <Entity, EntityType> EntityCheckType<Entity, EntityType> getEntityChecker();

    /**
     *
     */
    @ApiStatus.Experimental
    <Player> MessagingService<Player> getMessagingService();

    /**
     *
     */
    @ApiStatus.Experimental
    <Player> CheckIntersection<Player> getCheckIntersection();
}
