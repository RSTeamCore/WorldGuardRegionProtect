package net.ritasister.wgrp.api;

public interface WorldGuardRegionProtect {

    <T, P, R> RegionAdapter<T, P, R> getRegionAdapter();

    WorldGuardRegionMetadata getWorldGuardMetadata();

    void getConfig();
}
