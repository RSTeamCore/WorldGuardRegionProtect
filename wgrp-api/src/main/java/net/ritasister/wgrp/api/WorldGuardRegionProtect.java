package net.ritasister.wgrp.api;

import net.ritasister.wgrp.api.logging.PluginLogger;

public interface WorldGuardRegionProtect {

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
