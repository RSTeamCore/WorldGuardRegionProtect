package net.ritasister.wgrp.api.manager.regions;

import net.ritasister.wgrp.rslibs.exceptions.NoSelectionException;

import java.util.List;
import java.util.Map;

/**
 * Methods by which you can interact with WorldGuard Api
 *
 * @param <L> this is location
 * @param <P> this is an object as player
 * @param <R> this check where region is selected by //pos1 and //pos2
 */
public interface RegionAdapterManager<L, P, R> {

    /**
     * Check access in standing region by a Player used region name from HashMap and list of regions.
     *
     * @param location Return location of object.
     * @param regions  Maps of regions from WorldGuard.
     * @return location of object(example: block, entity, Player etc.)
     * @since 0.3.4
     */
    boolean checkStandingRegion(L location, Map<String, List<String>> regions);

    /**
     * Checking the access in a region without region name.
     *
     * @param location Location of object.
     * @return location of object(example: block, entity, Player etc.)
     * @since 0.3.4
     */
    boolean checkStandingRegion(L location);

    /**
     * Getting the name of the region where the object trying to interact with protected a region.
     *
     * @param location Location of object.
     * @return the name of the region.
     * @since 0.7.1
     */
    String getProtectRegionName(L location);

    /**
     * Getting the name of the region where the player trying to use WorldEdit.
     *
     * @param player Location of object.
     * @since 0.7.6
     */
    String getProtectRegionNameBySelection(final P player);

    /**
     * Check the intersection by the player for the method getProtectRegionName()
     *
     * @param selection get selection in the region by a Player.
     * @return location of Object.
     * @since 0.7.6
     */
    String getProtectRegionNameByIntersection(final R selection) throws NoSelectionException;

}
