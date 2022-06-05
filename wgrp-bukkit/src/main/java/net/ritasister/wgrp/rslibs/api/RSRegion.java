package net.ritasister.wgrp.rslibs.api;

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
import net.ritasister.wgrp.rslibs.api.interfaces.IRSRegion;
import net.ritasister.wgrp.rslibs.exceptions.NoSelectionException;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RSRegion implements IRSRegion {

    /**
     * Check access in standing region by player used region name from config.yml.
     *
     * @param location return location of object.
     * @param regions maps of regions from WorldGuard.
     *
     * @return location of object.
     */
    @Override
    public boolean checkStandingRegion(@NotNull Location location, @NotNull HashMap<String, List<String>> regions) {
        if (regions.get(location.getWorld().getName()) == null) return false;
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
        for (String regionName : regions.get(location.getWorld().getName())) {
            if (applicableRegionSet
                    .getRegions()
                    .stream()
                    .anyMatch(region -> regionName.equalsIgnoreCase(region.getId()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check access in standing region by player without region name.
     *
     * @param location location of object.
     *
     * @return location of object.
     */
    @Override
    public boolean checkStandingRegion(@NotNull Location location) {
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
        return (applicableRegionSet.size() != 0);
    }

    /**
     * Get region name.
     *
     * @param player location of object.
     */
    @Override
    public String getProtectRegionName(final Player player) {
        LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
        Region selection = null;
        try {
            selection = localSession.getSelection(BukkitAdapter.adapt(player.getWorld()));
        } catch (IncompleteRegionException ignored) {}
        try {
            return getProtectRegionNameByIntersection(selection);
        } catch (NoSelectionException ignored) {}
        return null;
    }

    /**
     * Get region name.
     *
     * @param selection get selection in the region by player.
     *
     * @return location of Object.
     */
    public String getProtectRegionNameByIntersection(final Region selection) throws NoSelectionException {
        if (selection instanceof CuboidRegion) {
            final BlockVector3 min = selection.getMinimumPoint();
            final BlockVector3 max = selection.getMaximumPoint();
            final RegionContainer rc = WorldGuard.getInstance().getPlatform().getRegionContainer();
            final RegionManager regions = rc.get(selection.getWorld());
            final ProtectedRegion __dummy__ = new ProtectedCuboidRegion("__dummy__", min, max);
            assert regions != null;
            ApplicableRegionSet applicableRegionSet = regions.getApplicableRegions(__dummy__);
            return applicableRegionSet
                    .getRegions()
                    .stream()
                    .map(ProtectedRegion::getId)
                    .collect(Collectors.joining());
        }
        throw new NoSelectionException();
    }

    /**
     * Get region name.
     *
     * @param location get location of Object in the region.
     *
     * @return location of Object.
     */
    public String getProtectName(@NotNull Location location) {
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
        return applicableRegionSet
                .getRegions()
                .stream()
                .map(ProtectedRegion::getId)
                .collect(Collectors.joining());
    }

    /**
     * Getting applicable region for set.
     *
     * @param location location of Object.
     *
     * @return location of Object.
     */
    private @NotNull ApplicableRegionSet getApplicableRegions(final @NotNull Location location) {
        return Objects.requireNonNull(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld())))
                .getApplicableRegions(BukkitAdapter.asBlockVector(location));
    }
}