package net.ritasister.wgrp.core.api;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.ritasister.wgrp.api.RegionAdapter;
import net.ritasister.wgrp.rslibs.exceptions.NoSelectionException;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class RegionAdapterImpl implements RegionAdapter<Location, Player, Region> {

    /**
     * Getting regions by location using the WG API.
     *
     * @param location Location of Object.
     * @return location of any Object.
     */

    @Contract("_ -> new")
    private @NotNull ApplicableRegionSet getApplicableRegions(final @NotNull Location location) {
        return Objects.requireNonNull(WorldGuard.getInstance().getPlatform().getRegionContainer()
                        .get(BukkitAdapter.adapt(location.getWorld())))
                .getApplicableRegions(BukkitAdapter.asBlockVector(location));
    }

    /**
     * Check access in standing region by a Player used region name from HashMap and list of regions.
     *
     * @param location Return location of object.
     * @param regions  Maps of regions from WorldGuard.
     * @return location of object(example: block, entity, Player etc.)
     */
    @Override
    public boolean checkStandingRegion(@NotNull Location location, @NotNull Map<String, List<String>> regions) {
        if (regions.get(location.getWorld().getName()) == null) {
            return false;
        }
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
        for (String regionName : regions.get(location.getWorld().getName())) {
            if (applicableRegionSet.getRegions().stream().anyMatch(region ->
                            regionName.equalsIgnoreCase(region.getId()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checking the access in a region without region name.
     *
     * @param location Location of object.
     * @return location of object(example: block, entity, Player etc.)
     */
    @Override
    public boolean checkStandingRegion(@NotNull Location location) {
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
        return (applicableRegionSet.size() != 0);
    }

    /**
     * Getting the name of the region where the object trying to interact with protected a region.
     *
     * @param location Location of object.
     * @return the name of the region.
     */
    @Override
    public String getProtectRegionName(Location location) {
        return this.getApplicableRegions(location)
                .getRegions()
                .stream()
                .map(ProtectedRegion::getId)
                .collect(Collectors.joining());
    }

    /**
     * Getting the name of the region where the player trying to use WorldEdit.
     *
     * @param player Location of object.
     */
    @Override
    public String getProtectRegionNameBySelection(final Player player) {
        LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
        Region selection = null;
        try {
            selection = localSession.getSelection(BukkitAdapter.adapt(player.getWorld()));
        } catch (IncompleteRegionException ignored) {
        }
        try {
            return getProtectRegionNameByIntersection(selection);
        } catch (NoSelectionException ignored) {}
        return null;
    }

    /**
     * Check the intersection by the player for the method getProtectRegionName()
     *
     * @param selection get selection in the region by a Player.
     * @return location of Object.
     */
    @Override
    public String getProtectRegionNameByIntersection(final Region selection) throws NoSelectionException {
        if (selection instanceof CuboidRegion) {
            final BlockVector3 min = selection.getMinimumPoint();
            final BlockVector3 max = selection.getMaximumPoint();
            final RegionContainer rc = WorldGuard.getInstance().getPlatform().getRegionContainer();
            final RegionManager regions = rc.get(selection.getWorld());
            final ProtectedRegion __dummy__ = new ProtectedCuboidRegion("__dummy__", min, max);
            ApplicableRegionSet applicableRegionSet = Objects.requireNonNull(regions).getApplicableRegions(__dummy__);
            return applicableRegionSet
                    .getRegions()
                    .stream()
                    .map(ProtectedRegion::getId)
                    .collect(Collectors.joining());
        }
        throw new NoSelectionException();
    }

}
