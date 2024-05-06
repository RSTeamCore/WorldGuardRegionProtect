package net.ritasister.wgrp.api;

import net.ritasister.wgrp.api.logging.PluginLogger;

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
     * @param <L>
     * @param <P>
     * @param <R>
     * @return
     */
    <L, P, R> RegionAdapter<L, P, R> getRegionAdapter();

    /**
     * todo
     * @return getWorldGuardMetadata
     */
    WorldGuardRegionMetadata getWorldGuardMetadata();

    /**
     * get message helping
     * @param consoleSender
     * @param format
     */
    default void messageToCommandSender(Class<?> consoleSender, String format) {
    }

    /**
     * Gets the plugin logger
     *
     * @return the logger
     */
    PluginLogger getPluginLogger();
}
