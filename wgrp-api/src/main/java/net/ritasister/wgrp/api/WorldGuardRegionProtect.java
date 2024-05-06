package net.ritasister.wgrp.api;

public interface WorldGuardRegionProtect {

    <T, P, R> RegionAdapter<T, P, R> getRegionAdapter();

    WorldGuardRegionMetadata getWorldGuardMetadata();

    default void messageToCommandSender(Class<?> consoleSender, String format) {
    }
}
