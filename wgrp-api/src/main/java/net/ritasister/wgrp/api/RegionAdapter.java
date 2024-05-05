package net.ritasister.wgrp.api;

import net.ritasister.wgrp.rslibs.exceptions.NoSelectionException;

import java.util.List;
import java.util.Map;

public interface RegionAdapter<T, P, R> {

    boolean checkStandingRegion(T location, Map<String, List<String>> regions);

    boolean checkStandingRegion(T location);

    String getProtectRegionName(T location);

    String getProtectRegionNameBySelection(final P player);

    String getProtectRegionNameByIntersection(final R selection) throws NoSelectionException;

}
