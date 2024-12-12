package net.ritasister.wgrp.api.manager.regions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A region adapter manager for easy use WorldGuard Api
 */
public interface RegionAdapterManager<L, P> {

    /**
     * Check access in a standing region by a Player used region name from HashMap and list of regions.
     *
     * @param location Return location of an object.
     * @param regions  Maps of regions from WorldGuard.
     * @param uniqueId whether the player owner this region.
     * @return <code>owner</code> of the region.
     * @since 1.6.21
     */
    boolean isOwnerRegion(@NotNull L location, @NotNull Map<String, List<String>> regions, UUID uniqueId);

    /**
     * Check access in a standing region by a Player.
     *
     * @param location Return location of an object.
     * @param uniqueId whether the player owner this region.
     * @return <code>owner</code> of the region.
     * @since 1.7.21
     */
    boolean isOwnerRegion(@NotNull L location, UUID uniqueId);

    /**
     * Check access in a standing region by a Player used region name from HashMap and list of regions.
     *
     * @param location Return location of an object.
     * @param regions  Maps of regions from WorldGuard.
     * @param uniqueId whether the player owner this region.
     * @return <code>member</code> of the region.
     * @since 1.6.21
     */
    boolean isMemberRegion(@NotNull L location, @NotNull Map<String, List<String>> regions, UUID uniqueId);

    /**
     * Check access in a standing region by a Player.
     *
     * @param location Return location of an object.
     * @param uniqueId whether the player owner this region.
     * @return <code>member</code> of the region.
     * @since 1.7.21
     */
    boolean isMemberRegion(@NotNull L location, UUID uniqueId);

    /**
     * Check access in a standing region by a Player.
     *
     * @param location Return location of an object.
     * @return <code>member</code> of the region.
     * @since 1.9.21
     */
    boolean isPriorityRegion(@NotNull L location);

    /**
     * Check access in a standing region by a Player try interaction with region from HashMap.
     *
     * @param location Return location of an object.
     * @param regions  Maps of regions from WorldGuard.
     * @return represent <code>object</code> who's try to interact with region (example: block, entity, Player etc.)
     * @since 0.3.4
     */
    boolean checkStandingRegion(L location, Map<String, List<String>> regions);

    /**
     * Checking the access in a region without region name.
     *
     * @param location Location of an object.
     * @return represent <code>object</code> who's try to interact with region (example: block, entity, Player etc.)
     * @since 0.3.4
     */
    boolean checkStandingRegion(L location);

    /**
     * Getting the name of the region where the object trying to interact with protected a region.
     *
     * @param location Location of an object.
     * @return the <code>name</code> of the region.
     * @since 0.7.1
     */
    String getProtectRegionName(L location);

    /**
     * Getting the name of the region where the player trying to selection from WorldEdit.
     *
     * @param player Location of an object.
     * @since 0.7.6
     */
    String getProtectRegionNameBySelection(final P player);

}
