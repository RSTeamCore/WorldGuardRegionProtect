package net.ritasister.wgrp.api;

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
}
